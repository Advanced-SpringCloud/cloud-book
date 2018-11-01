package org.springframework.cloud.stream.binder.rocket;

import org.springframework.cloud.stream.binder.rocket.utils.CommonUtils;
import org.springframework.core.AttributeAccessor;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.integration.support.ErrorMessageUtils;
import org.springframework.messaging.support.ErrorMessage;

import java.util.Collections;
import java.util.Map;

public class RawRecordHeaderErrorMessageStrategy implements ErrorMessageStrategy {

    @Override
    public ErrorMessage buildErrorMessage(Throwable payload, AttributeAccessor attributes) {
        Object inputMessage = attributes.getAttribute(ErrorMessageUtils.INPUT_MESSAGE_CONTEXT_KEY);
        Map<String, Object> headers = Collections.singletonMap(CommonUtils.ROCKETMQ_RAW_DATA,
                attributes.getAttribute(CommonUtils.ROCKETMQ_RAW_DATA));
        // TODO: EnhancedErrorMessage deprecated
        return new ErrorMessage(payload, headers);
    }

}
