package oncall;

import oncall.controller.OnCallController;
import oncall.global.config.DIConfig;

public class Application {
    public static void main(String[] args) {
        DIConfig diConfig = new DIConfig();
        OnCallController oncallController = diConfig.oncallController();
        oncallController.run();
    }
}
