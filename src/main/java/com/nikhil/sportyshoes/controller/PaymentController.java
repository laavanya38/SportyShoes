package com.nikhil.sportyshoes.controller;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nikhil.sportyshoes.model.Cart;
import com.nikhil.sportyshoes.model.PaytmDetails;
import com.nikhil.sportyshoes.model.Product;
import com.paytm.pg.merchant.PaytmChecksum;

@Controller
public class PaymentController {
	
	@Autowired
	PaytmDetails paytmDetails;

	@GetMapping(value = "/pgredirect")
	public ModelAndView getRedirect() throws Exception {
		
		Random random = new Random();
		String customerId = "74674648";
		String transactionAmount = String.valueOf(Cart.cart.stream().mapToDouble(Product::getPrice).sum());
		String orderId = "ORDERID"+String.valueOf(random.nextLong());
		
		ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetails.getPaytmUrl());
		TreeMap<String, String> parameters = new TreeMap<>();
		paytmDetails.getDetails().forEach((k, v) -> parameters.put(k, v));
		parameters.put("MOBILE_NO", "9883807696");
		parameters.put("EMAIL", "golu.nikhil4u@gmail.com");
		parameters.put("ORDER_ID", orderId);
		parameters.put("TXN_AMOUNT", transactionAmount);
		parameters.put("CUST_ID", customerId);
		String checkSum = getCheckSum(parameters);
		parameters.put("CHECKSUMHASH", checkSum);
		modelAndView.addAllObjects(parameters);
		return modelAndView;
	}


	@PostMapping("/pgresponse")
	public String getResponseRedirect(HttpServletRequest request, Model model) {

		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		mapData.forEach((key, val) -> parameters.put(key, val[0]));
		String paytmChecksum = "";
		if (mapData.containsKey("CHECKSUMHASH")) {
			paytmChecksum = mapData.get("CHECKSUMHASH")[0];
		}
		String result;

		boolean isValideChecksum = false;
		System.out.println("RESULT : "+parameters.toString());
		try {
			isValideChecksum = validateCheckSum(parameters, paytmChecksum);
			System.out.println(isValideChecksum);
			System.out.println(isValideChecksum);
			if (isValideChecksum && parameters.containsKey("RESPCODE")) {
				if (parameters.get("RESPCODE").equals("01")) {
					result = "Payment Successful";
				} else {
					result = "Payment Failed";
				}
			} else {
				result = "Checksum mismatched";
			}
		} catch (Exception e) {
			result = e.toString();
		}
		model.addAttribute("total", Cart.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", Cart.cart);
		model.addAttribute("result",result);
		parameters.remove("CHECKSUMHASH");
		model.addAttribute("parameters",parameters);
		return "report";
	}

	private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
		return PaytmChecksum.verifySignature(parameters, paytmDetails.getMerchantKey(), paytmChecksum);
	}


	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return PaytmChecksum.generateSignature(parameters, paytmDetails.getMerchantKey());
	}

}
