package org.maxkey.web.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.dao.service.ReportService;
import org.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = { "/report" })
public class ReportLoginController {
	final static Logger _logger = LoggerFactory.getLogger(ReportLoginController.class);

	@Autowired
	@Qualifier("reportService")
	private ReportService reportService;
	
	
	@RequestMapping(value = { "/login/day" })
	public ModelAndView dayReport(
			@RequestParam(value="reportDate",required=false) String  reportDate) {
		ModelAndView modelAndView=new ModelAndView("report/login/day");
		if(reportDate==null||reportDate.equals("")){
			DateTime currentdateTime = new DateTime();
			reportDate=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd"));
		}
		
		List<Map<String, Object>> listDayReport=reportService.analysisDay(reportDate);
		Integer[] dayReportArray=new Integer[24];
		for(int i=0;i<24;i++){
			dayReportArray[i]=0;
		}
		for(Map<String, Object> record :listDayReport){
			Integer count=Integer.parseInt(record.get("REPORTCOUNT").toString());
			int hour=Integer.parseInt(record.get("REPORTHOUR").toString());
			dayReportArray[hour]=count;
		}
		
		String jsonDayReport=JsonUtils.gson2Json(dayReportArray);
		_logger.info(""+jsonDayReport);
		
		modelAndView.addObject("dayReportArray",jsonDayReport);
		modelAndView.addObject("reportDate",reportDate);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/login/month" })
	public ModelAndView monthReport(
			@RequestParam(value="reportDate",required=false) String  reportDate) {
		ModelAndView modelAndView=new ModelAndView("report/login/month");
		if(reportDate==null||reportDate.equals("")){
			DateTime currentdateTime = new DateTime();
			reportDate=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd"));
		}
		
		List<Map<String, Object>> listMonthReport=reportService.analysisMonth(reportDate);
		Integer[] monthReportArray=new Integer[31];
		for(int i=0;i<31;i++){
			monthReportArray[i]=0;
		}
		for(Map<String, Object> record :listMonthReport){
			Integer count=Integer.parseInt(record.get("REPORTCOUNT").toString());
			int day=Integer.parseInt(record.get("REPORTDAY").toString());
			monthReportArray[day-1]=count;
		}
		
		String jsonMonthReport=JsonUtils.gson2Json(monthReportArray);
		_logger.info(""+jsonMonthReport);
		
		modelAndView.addObject("jsonMonthReport",jsonMonthReport);
		modelAndView.addObject("reportDate",reportDate);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/login/year" })
	public ModelAndView yearReport(
			@RequestParam(value="reportDate",required=false) Integer  reportDate) {
		ModelAndView modelAndView=new ModelAndView("report/login/year");
		if(reportDate==null||reportDate.equals("")){
			DateTime currentdateTime = new DateTime();
			reportDate=Integer.parseInt(currentdateTime.toString( DateTimeFormat.forPattern("yyyy")));
		}
		
		List<Map<String, Object>> listMonthReport=reportService.analysisYear(reportDate);
		
		Integer[] monthReportArray=new Integer[12];
		for(int i=0;i<12;i++){
			monthReportArray[i]=0;
		}
		for(Map<String, Object> record :listMonthReport){
			Integer count=Integer.parseInt(record.get("REPORTCOUNT").toString());
			int month=Integer.parseInt(record.get("REPORTMONTH").toString());
			monthReportArray[month-1]=count;
		}
		
		String jsonMonthReport=JsonUtils.gson2Json(monthReportArray);
		_logger.info(""+jsonMonthReport);
		
		modelAndView.addObject("jsonMonthReport",jsonMonthReport);
		modelAndView.addObject("reportDate",reportDate);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/login/browser" })
	public ModelAndView browserReport(
			@RequestParam(value="startDate",required=false) String  startDate,
			@RequestParam(value="endDate",required=false) String  endDate) {
		ModelAndView modelAndView=new ModelAndView("report/login/browser");
		String startDateTime="";
		String endDateTime="";
		if(startDate==null||startDate.equals("")){
			DateTime currentdateTime = new DateTime();
			startDate=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd"));
			startDateTime=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00"));
		}else{
			startDateTime=startDate+" 00:00:00";
		}
		if(endDate==null||endDate.equals("")){
			DateTime currentdateTime = new DateTime();
			endDate=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd"));
			endDateTime=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59"));
		}else{
			endDateTime=endDate+" 23:59:59";
		}
		
		Map<String,Object> reportDate=new HashMap<String,Object>();
		reportDate.put("startDate", startDateTime);
		reportDate.put("endDate", endDateTime);
		List<Map<String, Object>> listReport=reportService.analysisBrowser(reportDate);
		
		Integer[] countArray=new Integer[listReport.size()];
		String [] categoryArray=new String[listReport.size()];
		String [] categoryPieArray=new String[listReport.size()];
		Map[] countPieArray=new HashMap[listReport.size()];
		for(int i=0;i<listReport.size();i++){
			countArray[i]=0;
		}
		int recordCount=listReport.size()-1;
		
		Integer countAll=0;
		for(Map<String, Object> record :listReport){
			countAll=countAll+Integer.parseInt(record.get("REPORTCOUNT").toString());
		}
		_logger.info("countAll : "+countAll);
		
		for(Map<String, Object> record :listReport){
			Integer count=Integer.parseInt(record.get("REPORTCOUNT").toString());
			String browser=record.get("BROWSER").toString();
			
			countArray[recordCount]=count;
			categoryArray[recordCount]=browser;
			String pieBrowser=((count*100f)/countAll)+"";
			if(pieBrowser.length()>5){
				pieBrowser=browser+"("+(pieBrowser.substring(0, 5))+"%)";
			}else{
				pieBrowser=browser+"("+pieBrowser+"%)";
			}
			Map<String,Object> nameValue=new HashMap<String,Object>();
			nameValue.put("name", pieBrowser);
			nameValue.put("value", count);
			countPieArray[recordCount]=nameValue;
			categoryPieArray[recordCount]=pieBrowser;
			
			_logger.info("count : "+count);
			
			recordCount--;
		}
		
		String jsonReport=JsonUtils.gson2Json(countArray);
		String categoryReport=JsonUtils.gson2Json(categoryArray);
		String pieReport=JsonUtils.gson2Json(countPieArray);
		String categoryPieReport=JsonUtils.gson2Json(categoryPieArray);
		_logger.info(""+jsonReport);
		
		modelAndView.addObject("jsonReport",jsonReport);
		modelAndView.addObject("categoryReport",categoryReport);
		modelAndView.addObject("categoryPieReport",categoryPieReport);
		modelAndView.addObject("pieReport",pieReport);
		modelAndView.addObject("startDate",startDate);
		modelAndView.addObject("endDate",endDate);
		return modelAndView;
	}
	
	@RequestMapping(value = { "/login/app" })
	public ModelAndView appReport(
			@RequestParam(value="startDate",required=false) String  startDate,
			@RequestParam(value="endDate",required=false) String  endDate) {
		ModelAndView modelAndView=new ModelAndView("report/login/app");
		String startDateTime="";
		String endDateTime="";
		if(startDate==null||startDate.equals("")){
			DateTime currentdateTime = new DateTime();
			startDate=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd"));
			startDateTime=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00"));
		}else{
			startDateTime=startDate+" 00:00:00";
		}
		if(endDate==null||endDate.equals("")){
			DateTime currentdateTime = new DateTime();
			endDate=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd"));
			endDateTime=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59"));
		}else{
			endDateTime=endDate+" 23:59:59";
		}
		
		Map<String,Object> reportDate=new HashMap<String,Object>();
		reportDate.put("startDate", startDateTime);
		reportDate.put("endDate", endDateTime);
		List<Map<String, Object>> listReport=reportService.analysisApp(reportDate);
		
		Integer[] countArray=new Integer[listReport.size()];
		String [] categoryArray=new String[listReport.size()];
		
		for(int i=0;i<listReport.size();i++){
			countArray[i]=0;
		}
		int recordCount=listReport.size()-1;
		for(Map<String, Object> record :listReport){
			Integer count=Integer.parseInt(record.get("REPORTCOUNT").toString());
			String app=record.get("APPNAME").toString();
			countArray[recordCount]=count;
			categoryArray[recordCount]=app;
			
			recordCount--;
		}
		
		String jsonReport=JsonUtils.gson2Json(countArray);
		String categoryReport=JsonUtils.gson2Json(categoryArray);
		
		_logger.info(""+jsonReport);
		
		modelAndView.addObject("jsonReport",jsonReport);
		modelAndView.addObject("categoryReport",categoryReport);
		modelAndView.addObject("startDate",startDate);
		modelAndView.addObject("endDate",endDate);
		return modelAndView;
	}
	
}
