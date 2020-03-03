package com.yy.ebp.eureka.discovery.controller;

import com.yy.framework.commons.collections.ExtendMap;
import com.yy.framework.core.reponse.Response;
import com.yy.framework.core.reponse.ResponseCallBack;
import com.yy.framework.core.reponse.ResponseCriteria;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.util.StatusInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/dashboard")
public class DiscoveryController {
	
	@RequestMapping(path="/services", method= RequestMethod.GET, produces = "application/json")
	public Response registeTable() {
		return new ResponseCallBack() {
			@Override
			public void execute(ResponseCriteria criteria, Object... obj) {
				EurekaServerContext context = EurekaServerContextHolder.getInstance().getServerContext();
				PeerAwareInstanceRegistry instanceRegistry = context.getRegistry();
				List<Application> applications = instanceRegistry.getSortedApplications();
				
				criteria.addSingleResult(applications);
			}
		}.sendRequest();
	}
	
	@RequestMapping(path="/status", method= RequestMethod.GET, produces = "application/json")
	public Response status() {
		return new ResponseCallBack() {
			@Override
			public void execute(ResponseCriteria criteria, Object... obj) {
				Runtime runtime = Runtime.getRuntime();
				int totalMem = (int) (runtime.totalMemory() / 1048576);
				int freeMem = (int) (runtime.freeMemory() / 1048576);
				int usedPercent = (int) (((float) totalMem - freeMem) / (totalMem) * 100.0);
				
				ExtendMap<String, Object> status = new ExtendMap<>();
				status.put("num-of-cpus", String.valueOf(runtime.availableProcessors()));
				status.put("total-avail-memory", String.valueOf(totalMem) + "mb");
				status.put("current-memory-usage",  String.valueOf(totalMem - freeMem) + "mb (" + usedPercent + "%)");
				status.put("server-uptime", StatusInfo.getUpTime());
				status.put("environment", ConfigurationManager.getDeploymentContext().getDeploymentEnvironment());
				status.put("instance", ApplicationInfoManager.getInstance().getInfo());
				
				criteria.addSingleResult(status);
			}
		}.sendRequest();
	}

}
