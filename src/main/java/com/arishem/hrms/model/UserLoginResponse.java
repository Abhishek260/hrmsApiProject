package com.arishem.hrms.model;

public class UserLoginResponse {
    private int commandstatus;
    private String commandmessage;
    private Integer userid;
    private String displayname;
    private String imagepath;

    public UserLoginResponse(int commandstatus, String commandmessage, Integer userid, String displayname, String imagepath) {
        this.commandstatus = commandstatus;
        this.commandmessage = commandmessage;
        this.userid = userid;
        this.displayname = displayname;
        this.imagepath = imagepath;
    }

    // Getters and Setters
    public int getCommandstatus() { return commandstatus; }
    public void setCommandstatus(int commandstatus) { this.commandstatus = commandstatus; }

    public String getCommandmessage() { return commandmessage; }
    public void setCommandmessage(String commandmessage) { this.commandmessage = commandmessage; }

    public Integer getUserid() { return userid; }
    public void setUserid(Integer userid) { this.userid = userid; }

    public String getDisplayname() { return displayname; }
    public void setDisplayname(String displayname) { this.displayname = displayname; }

    public String getImagepath() { return imagepath; }
    public void setImagepath(String imagepath) { this.imagepath = imagepath; }
}
