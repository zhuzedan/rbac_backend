package org.zzd.utils;

import org.zzd.entity.SystemMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zzd
 * @apiNote :递归菜单工具
 * @date : 2023-03-19 21:53
 */
public class MenuHelper {
    /**
     * @apiNote 递归创建菜单
     * @param sysMenuList: 菜单
     * @return java.util.List<org.zzd.entity.SystemMenu>
     */
    public static List<SystemMenu> buildTree(List<SystemMenu> sysMenuList) {
        List<SystemMenu> trees = new ArrayList<>();
        for (SystemMenu systemMenu : sysMenuList) {
            if (systemMenu.getParentId() == 0) {
                trees.add(findChildren(systemMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * @apiNote 递归查找子节点
     * @param systemMenu: 菜单
     * @param treeNodes: 菜单树
     * @return org.zzd.entity.SystemMenu
     */
    public static SystemMenu findChildren(SystemMenu systemMenu, List<SystemMenu> treeNodes) {
        systemMenu.setChildren(new ArrayList<SystemMenu>());

        for (SystemMenu it : treeNodes) {
            if(systemMenu.getId().longValue() == it.getParentId().longValue()) {
                if (systemMenu.getChildren() == null) {
                    systemMenu.setChildren(new ArrayList<>());
                }
                systemMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return systemMenu;
    }
}
