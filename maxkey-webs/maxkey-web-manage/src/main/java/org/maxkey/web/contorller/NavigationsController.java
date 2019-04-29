package org.maxkey.web.contorller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.maxkey.dao.service.NavigationsService;
import org.maxkey.domain.Navigations;
import org.maxkey.web.WebContext;
import org.maxkey.web.component.TreeNode;
import org.maxkey.web.component.TreeNodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 菜单操作管理
 * 
 * @author Crystal.sea
 *
 */
@Controller
@RequestMapping(value={"/navigations"})
public class NavigationsController {
	final static Logger _logger = LoggerFactory.getLogger(NavigationsController.class);
	
	@Autowired
	@Qualifier("navigationsService")
	NavigationsService navigationsService;

	
	/**
	 * 菜单国际化的转换
	 * 
	 * @param listNavigations
	 */
	private void convert2LocaleNavigation(List<Navigations> listNavigations){
		if(listNavigations!=null&&listNavigations.size()>0){
			for(Navigations navigation:listNavigations){
				String navigationName=WebContext.getI18nValue("navs."+navigation.getId());
				String pNavigationName=WebContext.getI18nValue("navs."+navigation.getpId());
				if(navigationName!=null){
					navigation.setName(navigationName);
				}
				if(pNavigationName!=null){
					navigation.setpName(pNavigationName);
				}
	        }
		}
	}
	
	/**
	 * 查询所有菜单树
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/tree"})
	public List<HashMap<String,Object>> navigationsTree(@RequestParam(value = "id", required = false)String id){
		_logger.debug("navigationsTree id :"+id);
		
		List<Navigations>   navigationsList=navigationsService.query(null);
		convert2LocaleNavigation(navigationsList);
		
		TreeNodeList treeNodeList=new TreeNodeList();
		for(Navigations  navigation: navigationsList){
			TreeNode treeNode=new TreeNode(navigation.getId(),navigation.getName());
			if(navigation.getHasChild()!=null&&navigation.getHasChild().equals('Y')){
				treeNode.setHasChild();
			}
			
			//if(id.equals("-1"))treeNode.setAttr("open", true);
			treeNode.setAttr("data", navigation);
			treeNode.setPId( navigation.getpId());
			if(navigation.getId().equals(WebContext.getSystemNavRootId())){
				treeNode.setAttr("open", true);
			}else{
				treeNode.setAttr("open", false);
			}
			treeNodeList.addTreeNode(treeNode.getAttr());
		}
		

		return treeNodeList.getTreeNodeList();
	}
	
	
	/**
	 * 获取全部具有权限的菜单
	 * @param pId
	 * @param navigation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/load"})
	public List<Navigations> loadNavigation(){
		List<Navigations> listNavigations =WebContext.getNavigations();
		convert2LocaleNavigation(listNavigations);
		return  listNavigations;
		
	}
	
	/**
	 * 获取一级菜单
	 * @param pId
	 * @param navigation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/onelevelchild"})
	public List<Navigations> navigationsOneLevelChild(@RequestParam(value = "pId", required = false)String pId,@ModelAttribute("navigation") Navigations navigation){
		_logger.debug("navigationsTree pId :"+pId);
		ArrayList<Navigations> topLevelNavigations =new ArrayList<Navigations>();
		for(Navigations cnavigation : WebContext.getNavigations()){
			if(cnavigation.getpId()!=null&&cnavigation.getpId().equals(WebContext.getSystemNavRootId())){
				topLevelNavigations.add(cnavigation);
				_logger.debug("=========== Navigation id : "+cnavigation.getpId()+" , Navigation Name : "+cnavigation.getName());
			}
		}
		convert2LocaleNavigation(topLevelNavigations);
		return  topLevelNavigations;
		
	}
	
	/**
	 * 获取二、三级菜单
	 * @param pId
	 * @param navigation
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/leftchild"})
	public List<Navigations> navigationsLeftChild(@RequestParam(value = "pId", required = false)String pId,@ModelAttribute("navigation") Navigations navigation){
		_logger.debug("navigationsTree pId :"+pId);
		ArrayList<Navigations> left1LevelNavigations =new ArrayList<Navigations>();
		for(Navigations cnavigation : WebContext.getNavigations()){
			if(cnavigation.getpId()!=null&&cnavigation.getpId().equals(pId)){
				_logger.debug("=========== Navigation id : "+cnavigation.getpId()+" , Navigation Name : "+cnavigation.getName());
				ArrayList<Navigations> left2LevelNavigations =new ArrayList<Navigations>();
				for(Navigations lnavigation : WebContext.getNavigations()){
					if(lnavigation.getpId()!=null&&lnavigation.getpId().equals(cnavigation.getId())){
						left2LevelNavigations.add(lnavigation);
						_logger.debug("======================= Navigation id : "+lnavigation.getpId()+" , Navigation Name : "+lnavigation.getName());
					}
				}
				convert2LocaleNavigation(left2LevelNavigations);
				cnavigation.setChildNavs(left2LevelNavigations);
				left1LevelNavigations.add(cnavigation);
			}
		}
		convert2LocaleNavigation(left1LevelNavigations);
		_logger.debug("left1LevelNavigations size : "+left1LevelNavigations.size());
		return  left1LevelNavigations;
		
	}
	
}
