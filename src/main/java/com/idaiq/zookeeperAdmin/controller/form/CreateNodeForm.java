package com.idaiq.zookeeperAdmin.controller.form;

import lombok.Data;

@Data
public class CreateNodeForm {
    private String path;
    private String parentPath;
    private String data;
    private String acl;
}
