package oncall;

import oncall.controller.OncallController;
import oncall.global.config.DIConfig;

public class Application {
    public static void main(String[] args) {
        DIConfig diConfig = new DIConfig();
        OncallController oncallController = diConfig.oncallController();
        oncallController.run();
    }
}
