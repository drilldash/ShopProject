package netcracker.edu.ishop.utils.commands;
import com.google.gson.JsonObject;


public class CommandFormat {

    public static final JsonObject commandTemplate = new JsonObject();

    //public static final StringTemplate commandTemplate = new StringTemplate("{\"STATUS\":\"$status$\",\"CODE\":\"$code$\",\"Content\":\"$content$\"}", DefaultTemplateLexer.class);


//    public CommandFormat(String status, String code, String content) {
//        this.commandTemplate.setAttribute("STATUS", status);
//        this.commandTemplate.setAttribute("CODE", code);
//        this.commandTemplate.setAttribute("Content", content);
//    }

//    public static String build(String status, String code, String content) {
//        commandTemplate.setAttribute("status", status);
//        commandTemplate.setAttribute("code", code);
//        commandTemplate.setAttribute("content", content);
//
//        return commandTemplate.toString();
//    }


    public static String build(String status, String code, String content) {
        commandTemplate.addProperty(CommandOutputFields.STATUS.toString(), status);
        commandTemplate.addProperty(CommandOutputFields.CODE.toString(), code);
        commandTemplate.addProperty(CommandOutputFields.CONTENT.toString(), content);
        return commandTemplate.toString();
    }

}
