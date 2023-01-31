package com.cheese.boot.core.secure.domain;

import java.util.List;

/**
 * 系统权限
 *
 * @author sobann
 */
public interface ISysPermission {

    String getUrl();

    List<String> getPermissions();
}
