package com.dogoo.SystemWeighingSas.dao;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import com.dogoo.SystemWeighingSas.model.WeightSlipCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class WeightSlipCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public WeightSlipCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<WeightSlip> findAllWithFilters(Integer limit,
                                               Integer page,
                                               WeightSlipCriteria weightSlipCriteria ){
        CriteriaQuery<WeightSlip> criteriaQuery = criteriaBuilder.createQuery(WeightSlip.class);
        Root<WeightSlip> weightSlipRoot = criteriaQuery.from(WeightSlip.class);
        Predicate predicate = getPredicate(weightSlipCriteria, weightSlipRoot);
        criteriaQuery.where(predicate);
        setOrder(criteriaQuery, weightSlipRoot);

        TypedQuery<WeightSlip> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * limit);
        typedQuery.setMaxResults(limit);

        Pageable pageable = PageRequest.of(page, limit);

        long employeesCount = getEmployeesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(WeightSlipCriteria weightSlipCriteria,
                                   Root<WeightSlip> weightSlipRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(weightSlipCriteria.getDatabaseKey())){
            predicates.add(
                    criteriaBuilder.like(weightSlipRoot.get("databaseKey"),
                            weightSlipCriteria.getDatabaseKey())
            );
        }

        if (Objects.nonNull(weightSlipCriteria.getSearch())){
            Predicate predicateKhachHang
                    = criteriaBuilder.like(weightSlipRoot.get("khachHang"),
                    '%' + weightSlipCriteria.getSearch() + '%');
            Predicate predicateTenHang
                    = criteriaBuilder.like(weightSlipRoot.get("tenHang"),
                    '%' + weightSlipCriteria.getSearch() + '%');
            predicates.add(criteriaBuilder.or(predicateTenHang, predicateKhachHang));
        }

        if(Objects.nonNull(weightSlipCriteria.getSoXe())){
            List<Predicate> list = new ArrayList<>();
            weightSlipCriteria.getSoXe().forEach(s -> {
                Predicate predicateForGradeA
                        = criteriaBuilder.equal(weightSlipRoot.get("soXe"), s);
                list.add(predicateForGradeA);
            });

            predicates.add(criteriaBuilder.or(list.toArray(new Predicate[0])));
        }
        if(Objects.nonNull(weightSlipCriteria.getTenHang())){
            List<Predicate> list = new ArrayList<>();
            weightSlipCriteria.getSoXe().forEach(s -> {
                Predicate predicateForGradeA
                        = criteriaBuilder.equal(weightSlipRoot.get("tenHang"), s);
                list.add(predicateForGradeA);
            });

            predicates.add(criteriaBuilder.or(list.toArray(new Predicate[0])));
        }
        if(Objects.nonNull(weightSlipCriteria.getKhachHang())){
            List<Predicate> list = new ArrayList<>();
            weightSlipCriteria.getSoXe().forEach(s -> {
                Predicate predicateForGradeA
                        = criteriaBuilder.equal(weightSlipRoot.get("khachHang"), s);
                list.add(predicateForGradeA);
            });

            predicates.add(criteriaBuilder.or(list.toArray(new Predicate[0])));
        }

        if (weightSlipCriteria.getStartDate() != null && weightSlipCriteria.getEndDate() != null){
            LocalDateTime startDate = weightSlipCriteria.getStartDate().withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endDate = weightSlipCriteria.getEndDate().withHour(23).withMinute(59).withSecond(59).withNano(0);
            predicates.add(
                    criteriaBuilder.between(weightSlipRoot.get("ngayCan"),
                            Timestamp.valueOf(startDate), Timestamp.valueOf(endDate))
            );
        }

        if (weightSlipCriteria.getStartDate() != null && weightSlipCriteria.getEndDate() == null){
            LocalDateTime startDate = weightSlipCriteria.getStartDate().withHour(0).withMinute(0).withSecond(0).withNano(0);
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(weightSlipRoot.get("ngayCan"),
                            Timestamp.valueOf(startDate))
            );
        }

        if (weightSlipCriteria.getStartDate() == null && weightSlipCriteria.getEndDate() != null){
            LocalDateTime endDate = weightSlipCriteria.getEndDate().withHour(23).withMinute(59).withSecond(59).withNano(0);
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(weightSlipRoot.get("ngayCan"),
                            Timestamp.valueOf(endDate))
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(CriteriaQuery<WeightSlip> criteriaQuery,
                          Root<WeightSlip> weightSlipRoot) {
        criteriaQuery.orderBy(criteriaBuilder.desc( weightSlipRoot.get("ngayCan")));
    }

    private long getEmployeesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<WeightSlip> countRoot = countQuery.from(WeightSlip.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
