package oncall.global.config;

import oncall.controller.OncallController;
import oncall.global.validator.Validator;
import oncall.view.InputView;
import oncall.view.OutputView;

public class DIConfig {

    public OncallController oncallController() {
        return new OncallController(
                inputView(),
                outputView(),
                validator()
        );
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public Validator validator() {
        return new Validator();
    }
}
