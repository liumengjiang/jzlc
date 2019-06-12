package com.zl.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zl.pojo.Product;
import com.zl.pojo.ProductCheckList;
import com.zl.pojo.RealAuthShow;
import com.zl.service.IBankCardInfoService;
import com.zl.service.IConsumerInfoService;
import com.zl.util.BitStateUtil;
import com.zl.util.CheckLogin;
/**
 * 所有页面的跳转
 * @author ivy
 *
 */
@Controller
public class TransferController {
	@Autowired
	private IConsumerInfoService consumerInfoService;
	@Autowired
	private IBankCardInfoService bankCardInfoService;
	
	/**跳转登录页面*/
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	/** 跳转注册页面*/
	@RequestMapping("register")
	public String register() {
		return "register";
	}


	/**企业注册页面1*/
	@RequestMapping("eregister")
	public String eregister() {
		return "enterprise/eregister";
	}


	/**企业注册页面2*/
	@RequestMapping("eregister2")
	public String eregister2() {
		return "enterprise/eregister2";
	}
	

	@RequestMapping("elogin")
	public String elogin() {
		return "enterprise/elogin";
	}

	


	/**跳转添加产品页面*/
	@RequestMapping("addProduct")
	public String addProduct(Product product) {
		return "enterprise/addProduct";
	}
	
	/**产品审核记录页面*/
	@RequestMapping("productAuditProgress")
	public String productAuditProgress(ProductCheckList productCheckList) {
		return "enterprise/productAuditProgress";
	}
	
	/**个人产品审核列表页面*/
	@RequestMapping("personalProductList")
	public String personalProductList(Product product) {
		return "personal/personalProductList";
	}
	
	/**个人中心页面*/
	@RequestMapping("personalCenter")
	@CheckLogin
	public String personalCenter(String id) {
		return "personal/personalCenter";
	}
	
	/**理财产品列表页面*/
	@RequestMapping("productInfo")
	public String productInfo(String id) {
		return "personal/productInfo";
	}
	
	/**安全保障页面*/
	@RequestMapping("safety")
	public String safety(String id) {
		return "indexinfo/safety";
	}
	
	/**公司简介页面*/
	@RequestMapping("aboutUs")
	public String aboutUs(String id) {
		return "indexinfo/aboutUs";
	}
	
	/**操作成功页面*/
	@RequestMapping("transferSuccessed")
	public String transferSuccessed(String id) {
		return "personal/transferSuccessed";
	}
	
	/***/
	@RequestMapping("receivableRecords")
	public String receivableRecords() {
		return "personal/receivableRecords";
	}
	
	/***/
	@RequestMapping("recharge")
	public String recharge() {
		return "personal/recharge";
	}

	/***/
	@RequestMapping("cashOut")
	public String cashOut() {
		return "personal/cashOut";
	}

	/***/
	@RequestMapping("myMoney")
	public String myMoney() {
		return "personal/myMoney";
	}

	/***/
	@RequestMapping("systemInformation")
	public String systemInformation() {
		return "personal/systemInformation";
	}

	/**账户设置页面*/
	@RequestMapping("accountSettings")
	@CheckLogin
	public String accountSettings(HttpServletRequest request) {
		String tel = consumerInfoService.queryTel();
		//身份证号
		String idCard = consumerInfoService.queryIdCard();
		//银行卡号数量
		int cardId = bankCardInfoService.queryCardId().size();
		String email = consumerInfoService.queryEmail();
		if(tel != null) {
			tel = tel.replaceAll(tel.substring(3, 7), "****");
			request.setAttribute("tel", tel);
		}
		if(idCard!=null) {
			idCard = idCard.replaceAll(idCard.substring(8, 14), "******");
			request.setAttribute("idCard", idCard);
		}
		if(cardId>0) {
			request.setAttribute("cardId", cardId);
		}
		if(email != null) {
			email = email.replaceAll(email.substring(3, 7), "****");
			request.setAttribute("email", email);
		}
		return "personal/accountSettings";
	}

	/**实名认证页面*/
	@RequestMapping("realAuth")
	public String realAuth() {
		return "personal/realAuth";
	}
	
	/**实名认证展示页面*/
	@RequestMapping("realAuthShow")
	@CheckLogin
	public String realAuthShow(HttpServletRequest requset) {
		RealAuthShow realAuthShow = consumerInfoService.queryRealAuthInfo();
		requset.setAttribute("realAuthShow", realAuthShow);
		return "personal/realAuthShow";
	}
	
	/**绑定银行卡页面*/
	@RequestMapping("bindBank")
	@CheckLogin
	public String bindBank(HttpServletRequest requset) {
		long bitState = consumerInfoService.queryBitState();
		String name = consumerInfoService.queryRealName();
		if(!BitStateUtil.hasState(bitState, BitStateUtil.OPEN_REAL_AUTH)) {
			requset.setAttribute("unRealAuth", "请先实名认证!");
			return "personal/realAuth";
		}
		if(!BitStateUtil.hasState(bitState, BitStateUtil.OPEN_BANKCARD)) {
			requset.setAttribute("isBindBank", "还未绑定银行卡");
		}
		requset.setAttribute("name", name);
		return "personal/bindBank";
	}
	
	/**查看银行卡页面*/
	@RequestMapping("queryBankCards")
	@CheckLogin
	public String queryBankCards(HttpServletRequest requset) {
		List<String> cardIds = bankCardInfoService.queryCardId();
		requset.setAttribute("cardIds", cardIds);
		return "personal/queryBankCards";
	}
	
	/**产品详情页面*/
	@RequestMapping("invest")
	public String invest() {
		return "personal/invest";
	}
	

}