//package edu.project.ruangong.utils;
//
//import org.springframework.beans.BeanUtils;
//import org.zucc.springbootsample.dto.PersonDTO;
//import org.zucc.springbootsample.form.PersonFrom;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class PersonFormConvertTODTO {
//    public static PersonDTO convertoTO(PersonFrom personFrom){
//
//        PersonDTO personDTO = new PersonDTO();
//        BeanUtils.copyProperties(personFrom,personDTO);
//        return  personDTO;
//
//    }
//
//    public static List<PersonDTO> converToDT(List<PersonFrom> personFromList){
//        return personFromList.stream().map(x->
//                convertoTO(x)
//        ).collect(Collectors.toList());
//    }
//}
