package com.dogoo.SystemWeighingSas.dao;

import com.dogoo.SystemWeighingSas.entity.WeightSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IWeightSlipDao extends JpaRepository<WeightSlip,Long> {

    @Query("SELECT COUNT(weight_slip_id) FROM DG_WeightSlip ")
    Integer getCountWeightSlip();

    @Query("select ws from DG_WeightSlip ws Where ws.key=:key AND ws.databaseKey=:databaseKey")
    List<WeightSlip> getAllWeightSlipByKeyAndDatabase(
            @Param("key") String key, @Param("databaseKey") String databaseKey);

    @Query("select ws from DG_WeightSlip ws Where ws.maPhieu=:maPhieu ")
    WeightSlip getWeightSlipByMaPhieu(@Param("maPhieu") String maPhieu);

    @Query(" DELETE FROM DG_WeightSlip WHERE maPhieu=:maPhieu ")
    void deleteWeightSlipByMaPhieu(@Param("maPhieu") String maPhieu);

//    @Modifying
//    @Query("update DG_WeightSlip u set u.khachHang=:khachHang, u.tenHang=:tenHang," +
//            " u.maHang=:maHang, u.maKH=:maKH, u.soXe=:soXe, u.coTai=:coTai, u.tareWeight=:tareWeight," +
//            " u.hang=:hang, u.ngayCan=:ngayCan, u.gioCoTai=:gioCoTai, u.gioKTai=gioKTai, u.ghiChu=:ghiChu," +
//            " u.xN=:xN, u.urlCoTai1=:urlCoTai1, u.urlCoTai2=:urlCoTai2, u.urlKoTai1=:urlKoTai1, u.urlKoTai2=:urlKoTai2," +
//            " u.chietKhau=:chietKhau, u.donGia=:donGia, u.sauCK=:sauCK, u.thanhTien=:thanhTien where u.maPhieu=:maPhieu")
//    void updateWeightSlipByMaPhieu(@Param("khachHang") String khachHang, @Param("tenHang") String tenHang ,
//                                   @Param("maHang") String maHang, @Param("maKH") String maKH,
//                                   @Param("soXe") String soXe, @Param("coTai") long coTai,
//                                   @Param("tareWeight") long tareWeight, @Param("hang") long hang,
//                                   @Param("ngayCan") Timestamp ngayCan, @Param("gioCoTai") Timestamp gioCoTai,
//                                   @Param("gioKTai") Timestamp gioKTai, @Param("ghiChu") String ghiChu,
//                                   @Param("xN") String xN, @Param("urlCoTai1") String urlCoTai1,
//                                   @Param("urlCoTai2") String urlCoTai2, @Param("urlKoTai1") String urlKoTai1,
//                                   @Param("urlKoTai2") String urlKoTai2, @Param("chietKhau") String chietKhau,
//                                   @Param("donGia") String donGia, @Param("sauCK") String sauCK,
//                                   @Param("thanhTien") Long thanhTien , @Param("maPhieu") String maPhieu);

    @Query("select ws from DG_WeightSlip ws ORDER BY ws.ngayCan DESC, ws.gioKTai DESC ")
    List<WeightSlip> getAllWeightSlipOrder();

    @Query("select ws from DG_WeightSlip ws where ws.action='delete' OR ws.action='update'" )
    List<WeightSlip> getAllWeightSlipAction();
}
