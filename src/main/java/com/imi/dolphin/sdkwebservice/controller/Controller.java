/**
 * Copyright (c) 2014 InMotion Innovation Technology. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of
 * InMotion Innovation Technology. ("Confidential Information").<BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall
 * only be used in accordance with the terms of the license agreement
 * entered into with IMI; other than in accordance with the written
 * permission of IMI. <BR>
 * 
 **/
package com.imi.dolphin.sdkwebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imi.dolphin.sdkwebservice.model.ExtensionRequest;
import com.imi.dolphin.sdkwebservice.model.ExtensionResult;
import com.imi.dolphin.sdkwebservice.property.AppProperties;
import com.imi.dolphin.sdkwebservice.service.IMailService;
import com.imi.dolphin.sdkwebservice.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 
 * @author reja
 * 
 */
@RestController
public class Controller {

	@Autowired
	private AppProperties appProperties;

	@Autowired
	IService svcService;
	
	@Autowired
	IMailService svcMailService;

	@RequestMapping("/forms")
	public String getStarted() {
		return "Hello Form, service port: " + appProperties.getServicePort() + ", " + appProperties.getFormId();
	}

	@RequestMapping("/status/")
	@PostMapping
	public ExtensionResult doGetSrnResult(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getSrnResult(extensionRequest);
	}

	@RequestMapping("/customers")
	@PostMapping
	public ExtensionResult doQueryCustomerInfo(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getCustomerInfo(extensionRequest);
	}

	@RequestMapping("/modifycustomername")
	@PostMapping
	public ExtensionResult doClearCustomerName(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.modifyCustomerName(extensionRequest);
	}
	
	@RequestMapping("/productinfo")
	@PostMapping
	public ExtensionResult doQueryProductInfo(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getProductInfo(extensionRequest);
	}

	@RequestMapping("/messages")
	@PostMapping
	public ExtensionResult doGetMessages(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getMessageBody(extensionRequest);
	}

	@RequestMapping("/quickreplies")
	@PostMapping
	public ExtensionResult doBuildQuickReplies(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getQuickReplies(extensionRequest);
	}

	@RequestMapping("/form")
	@PostMapping
	public ExtensionResult doBuildForm(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getForms(extensionRequest);
	}

	@RequestMapping("/button")
	@PostMapping
	public ExtensionResult doBuildButton(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getButtons(extensionRequest);
	}

	@RequestMapping("/carousel")
	@PostMapping
	public ExtensionResult doBuildCarousel(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getCarousel(extensionRequest);
	}
	
	@RequestMapping("/transferAgent")
	@PostMapping
	public ExtensionResult doTransferToAgent(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.doTransferToAgent(extensionRequest);
	}
	
            @RequestMapping("/sendLocation")
	@PostMapping
	public ExtensionResult doBuildSendLocation(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.doSendLocation(extensionRequest);
	}
	
	@RequestMapping("/image")
	@PostMapping
	public ExtensionResult doBuildImage(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.getImage(extensionRequest);
	}
	
	@RequestMapping("/sendMail")
	@PostMapping
	public ExtensionResult doSendMail(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.doSendMail(extensionRequest);
	}
	
	@RequestMapping("/formcuti")
	@PostMapping
	public ExtensionResult dogetFormcuti(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.dogetFormcuti(extensionRequest);
	}
        
	@RequestMapping("/formrequest")
	@PostMapping
	public ExtensionResult dogetFormrequest(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.doGetFormRequest(extensionRequest);
	}
        
	@RequestMapping("/formcomplaint")
	@PostMapping
	public ExtensionResult dogetFormComplaint(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.doGetFormComplaint(extensionRequest);
	}
        
	@RequestMapping("/formevent")
	@PostMapping
	public ExtensionResult dogetFormEventCCW(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.doGetFormEventCCW(extensionRequest);
	}
	
	@RequestMapping("/ajuincuti")
	@PostMapping
	public ExtensionResult dogetajuincuti(@RequestBody ExtensionRequest extensionRequest) {
		return svcService.dogetajuincuti(extensionRequest);
	}
        
        @RequestMapping("/cuaca")
        @PostMapping
        public ExtensionResult doGetCuaca(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetCuaca(extensionRequest);
        }
        
        @RequestMapping("/carlocation")
        @PostMapping
        public ExtensionResult getCarLocation(@RequestBody ExtensionRequest extensionRequest){
            return svcService.getCarLocation(extensionRequest);
        }
        
        @RequestMapping("/getSomethings")
        @PostMapping
        public ExtensionResult doGetSomethings(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetTerdekat(extensionRequest);
        }
        
        @RequestMapping("/tipeMobil")
        @PostMapping
        public ExtensionResult doGetCarType(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetTipeMobil(extensionRequest);
        }
        
        @RequestMapping("/merkMobils")
        @PostMapping
        public ExtensionResult doGetMerkMobils(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetMerkMobils(extensionRequest);
        }
        
         @RequestMapping("/modelMobils")
        @PostMapping
        public ExtensionResult doGetCarModels(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetModelMobils(extensionRequest);
        }
         @RequestMapping("/complaint")
        @PostMapping
        public ExtensionResult doComplaint(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doSendComplaint(extensionRequest);
        }
        
        @RequestMapping("/emailRequest")
        @PostMapping
        public ExtensionResult dosendRequest(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doSendEmailRequest(extensionRequest);
        }
        
        @RequestMapping("/emailSolution")
        @PostMapping
        public ExtensionResult dosendSolution(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doSendEmailSolution(extensionRequest);
        }
        
        @RequestMapping("/emailPromo")
        @PostMapping
        public ExtensionResult dosendPromo(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doSendEmailPromo(extensionRequest);
        }
        
        @RequestMapping("/ticketNumber")
        @PostMapping
        public ExtensionResult getTicketNumber(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetTicketNumber(extensionRequest);
        }
        
        @RequestMapping("/formData")
        @PostMapping
        public ExtensionResult getLastFormData(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetLastFormData(extensionRequest);
        }
        
        @RequestMapping("/validatephone")
        @PostMapping
        public ExtensionResult doValidatePhone(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doValidatePhone(extensionRequest);
        }
        
        @RequestMapping("/validatephoneb4form")
        @PostMapping
        public ExtensionResult doValidatePhoneB4Form(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doValidatePhoneSetNextEntity(extensionRequest);
        }
        
        @RequestMapping("/validateemail")
        @PostMapping
        public ExtensionResult doValidateEmail(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doValidateEmail(extensionRequest);
        }
        
        @RequestMapping("/yesnoconfirm")
        @PostMapping
        public ExtensionResult yesNoConfirm(@RequestBody ExtensionRequest extensionRequest){
            return svcService.yesNoConfirm(extensionRequest);
        }
        
        @RequestMapping("/merk17")
        @PostMapping
        public ExtensionResult merk17(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGet17MerkMobil(extensionRequest);
        }
        
        @RequestMapping("/ping")
        @PostMapping
        public ExtensionResult ping(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doPing(extensionRequest);
        }
        
        @RequestMapping("/setchance")
        @PostMapping
        public ExtensionResult setChance(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doSetChance(extensionRequest);
        }
        
        @RequestMapping("/checkvoucher")
        @PostMapping
        public ExtensionResult checkVoucher(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doCheckVoucher(extensionRequest);
        }
        
        @RequestMapping("/getresult")
        @PostMapping
        public ExtensionResult getResult(@RequestBody ExtensionRequest extensionRequest){
            return svcService.doGetResult(extensionRequest);
        }
        
        @RequestMapping("/maugaklocation")
        @PostMapping
        public ExtensionResult mauGakLocation(@RequestBody ExtensionRequest extensionRequest){
            return svcService.mauGakLocation(extensionRequest);
        }
        
        @RequestMapping("/latlongcity")
        @PostMapping
        public ExtensionResult latLongToCity(@RequestBody ExtensionRequest extensionRequest){
            return svcService.latLongToCity(extensionRequest);
        }
}
