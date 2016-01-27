package netcracker.edu.ishop.api.commands;

import com.google.gson.JsonObject;
import netcracker.edu.ishop.api.persistence.DAO;
import netcracker.edu.ishop.utils.UserGroupTypes;

import java.util.EnumSet;

public abstract class AbstractCommand{
    protected DAO daoInstance;
    protected EnumSet<UserGroupTypes> defaultLevelAccess;
    protected String statusMessage;
    protected JsonObject jsonCommandData = new JsonObject();

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
         return statusMessage;
    }

    public AbstractCommand(DAO daoInstance) {
        this.daoInstance = daoInstance;
    }

    public void setDefaultLevelAccess(EnumSet<UserGroupTypes> defaultLevelAccess) {
        this.defaultLevelAccess = defaultLevelAccess;
    }

    public abstract String getName();
    public abstract String getDescription();


    public abstract void execute(String[] cmdArgs);

    public abstract String toString();

    public boolean checkAccess(UserGroupTypes groupType) throws ClassCastException, NullPointerException {
        return defaultLevelAccess.contains(groupType);
    }

    public String getRequiredLevelAccess() {
        return defaultLevelAccess.toString();
    }

    public void setStatusCode(String code) {
        this.jsonCommandData.addProperty("STATUS_CODE", code);
    }

    public String getCmdStatusCode() {
        if (this.jsonCommandData.has("STATUS_CODE")) {
            return this.jsonCommandData.get("STATUS_CODE").getAsString();
        } else {
            return "";
        }
    }

    public void setCmdStatus(String status) {
        this.jsonCommandData.addProperty("STATUS", status);
    }

    public String getCmdStatus() {
        if (this.jsonCommandData.has("STATUS")) {
            return this.jsonCommandData.get("STATUS").getAsString();
        } else {
            return "";
        }
    }

    public void setCmdContent(String content) {
        this.jsonCommandData.addProperty("Content", content);
    }

    public String getCmdContent() {
        if (this.jsonCommandData.has("Content")) {
            return this.jsonCommandData.get("Content").getAsString();
        } else {
            return "";
        }
    }

    public void setAllCmdData(String status, String code, String content){
        this.jsonCommandData.addProperty("STATUS", status);
        this.jsonCommandData.addProperty("STATUS_CODE", code);
        this.jsonCommandData.addProperty("Content", content);
    }

    public JsonObject getCmdJsonCommandData() {
        return this.jsonCommandData;
    }

}
