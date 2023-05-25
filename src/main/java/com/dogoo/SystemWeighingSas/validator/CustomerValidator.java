package com.dogoo.SystemWeighingSas.validator;

import com.dogoo.SystemWeighingSas.dao.ICustomerDao;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.model.CustomerMapperModel;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerValidator {

    private static final String formatPhone1 = "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$";
    private static final String formatPhone2 = "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
    private static final String formatPhone3 = "|^0\\d{9,12}";
    private static final String formatPhone4 = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";
    private static final String PHONE_REG =
            formatPhone1 + formatPhone2 + formatPhone3 + formatPhone4;

    @Autowired
    private ICustomerDao iCustomerDao;

    public Response validatorCustomerCode (String customerCode) {
        Customer customer = iCustomerDao.findCustomerByCustomerCode(customerCode);

        if (customer == null)
            return null;

        return ResponseFactory.getClientErrorResponse("Mã khách hàng đã được sử dụng");
    }

    public Response validatorKey (String key) {
        Customer customer = iCustomerDao.findCustomerByKey(key);

        if (customer == null)
            return null;

        return ResponseFactory.getClientErrorResponse("Key đã được sử dụng");
    }

    public Response validatorPhoneNumber (String phoneNumber) {

        if (phoneNumber == null)
            return null;

        if (Pattern.matches(PHONE_REG, phoneNumber))
            return null;

        return ResponseFactory.getClientErrorResponse("Số điện thoại không đúng định dạng");
    }

    public Response validatorEmail (String email) {

        if (email == null)
            return null;

        if (EmailValidator.getInstance().isValid(email)){
            return null;
        }

        return ResponseFactory.getClientErrorResponse("Email không đúng định dạng");
    }

    public Response validatorDuplicateEmail (String email) {

        if (email == null)
            return null;

        Customer customer = iCustomerDao.findCustomerByEmail(email);
        if (customer == null)
            return null;

        return ResponseFactory.getClientErrorResponse("Email đã được sử dụng");
    }

    public Response validatorCustomerCodeUpdate (long id, String customerCode) {
        Customer customer = iCustomerDao.findCustomerByCustomerCode(customerCode);

        if (customer == null)
            return null;

        if (customer.getId() == id)
            return null;

        return ResponseFactory.getClientErrorResponse("Mã khách hàng đã được sử dụng");
    }

    public Response validatorDuplicateEmailUpdate (long id, String email) {

        if (email == null)
            return null;

        Customer customer = iCustomerDao.findCustomerByEmail(email);
        if (customer == null)
            return null;
        if (customer.getId() == id)
            return null;

        return ResponseFactory.getClientErrorResponse("Email đã được sử dụng");
    }

    public Response validatorExits (long id) {

        Optional<Customer> optionalCustomer = iCustomerDao.findById(id);
        if (optionalCustomer.isPresent())
            return null;

        return ResponseFactory.getClientErrorResponse("Không tồn tại khách hàng");
    }

    public Response validatorAdd (CustomerMapperModel model){
        Response responseCustomerCode = validatorCustomerCode(model.getCustomerCode());
        Response responseEmail = validatorEmail(model.getEmail());
        Response responseDuplicateEmail = validatorDuplicateEmail(model.getEmail());
        Response responsePhoneNumber = validatorPhoneNumber(model.getPhoneNumber());

        if (responseCustomerCode != null){
            return responseCustomerCode;
        }

        if (responseEmail != null){
            return responseEmail;
        }
        if (responseDuplicateEmail != null){
            return responseDuplicateEmail;
        }
        return responsePhoneNumber;
    }

    public Response validatorUpdate (long id, CustomerMapperModel model){
        Response responseExits = validatorExits(id);
        Response responseCustomerCode = validatorCustomerCodeUpdate(id,model.getCustomerCode());
        Response responseEmail = validatorEmail(model.getEmail());
        Response responseDuplicateEmail = validatorDuplicateEmailUpdate( id,model.getEmail());
        Response responsePhoneNumber = validatorPhoneNumber(model.getPhoneNumber());

        if (responseExits != null){
            return responseExits;
        }
        if (responseCustomerCode != null){
            return responseCustomerCode;
        }

        if (responseEmail != null){
            return responseEmail;
        }
        if (responseDuplicateEmail != null){
            return responseDuplicateEmail;
        }
        return responsePhoneNumber;
    }
}
