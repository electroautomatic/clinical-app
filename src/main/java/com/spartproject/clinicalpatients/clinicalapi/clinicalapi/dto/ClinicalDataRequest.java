package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.dto;

public class ClinicalDataRequest {


    public Long patientId;
    public String componentName;
    public String componentValue;
    
    public ClinicalDataRequest(String componentName, String componentValue) {
        this.componentName = componentName;
        this.componentValue = componentValue;
    }

    public ClinicalDataRequest(Long patientId, String componentName, String componentValue) {
        this.patientId = patientId;
        this.componentName = componentName;
        this.componentValue = componentValue;
    }

    public ClinicalDataRequest() {
        //TODO Auto-generated constructor stub
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(String componentValue) {
        this.componentValue = componentValue;
    }    

}
    


