package com.ccb.ga.cloud.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayController {

	@RequestMapping("/pay")
	public String pay() {
		return "pay";
	}
	
	@RequestMapping("/error")
	public String error() {
		return "pay-error";
	}
	
	@RequestMapping("/loading")
	public String login() {
		return "pay-loading";
	}
	
	@RequestMapping("/result")
	public String result() {
		return "pay-result";
	}
	
	@RequestMapping("/warning")
	public String warning() {
		return "pay-warning";
	}
}
