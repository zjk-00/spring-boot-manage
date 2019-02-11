package com.common.config.param;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * springboot获取配置文件内容的类
 * 
 * @author zjk
 * 2017年12月5日 下午5:56:34
 */
@Component
@PropertySource(value="classpath:param.properties",encoding="UTF-8")
public class ParamProperties {
	
	@Value("${server.host}") 
	private String server_host;
	@Value("${report.url}") 
	private String report_url;
	@Value("${nginx.url}")
	private String nginx_url;
	@Value("${xml.url}")
	private String xml_url;
	@Value("${pdf.url}")
	private String pdf_url;
	@Value("${score.url}")
	private String score_url;
	@Value("${model.url}")
	private String model_url;
	@Value("${model.pdf.url}")
	private String model_pdf_url;
	@Value("${down.url}")
	private String down_url;
	@Value("${pdf.path}")
	private String pdf_path;
	@Value("${dj.hishixi.stu.url}")
	private String dj_hishixi_stu_url;
	@Value("${dj.hishixi.hr.url}")
	private String dj_hishixi_hr_url;
	@Value("${dj.student.redirect.uri}")
	private String dj_student_redirect_uri;
	@Value("${dj.position.redirect.uri}")
	private String dj_position_redirect_uri;
	
	public String getServer_host() {
		return server_host;
	}
	public String getReport_url() {
		return report_url;
	}
	public String getNginx_url() {
		return nginx_url;
	}
	public String getXml_url() {
		return xml_url;
	}
	public String getPdf_url() {
		return pdf_url;
	}
	public String getScore_url() {
		return score_url;
	}
	public String getModel_url() {
		return model_url;
	}
	public String getModel_pdf_url() {
		return model_pdf_url;
	}
	public String getDown_url() {
		return down_url;
	}
	public String getPdf_path() {
		return pdf_path;
	}
	public String getDj_hishixi_stu_url() {
		return dj_hishixi_stu_url;
	}
	public String getDj_hishixi_hr_url() {
		return dj_hishixi_hr_url;
	}
	public String getDj_student_redirect_uri() {
		return dj_student_redirect_uri;
	}
	public String getDj_position_redirect_uri() {
		return dj_position_redirect_uri;
	}
	
}
