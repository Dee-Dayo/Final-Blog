package africa.semicolon.services;

import africa.semicolon.data.models.View;
import africa.semicolon.dto.requests.ViewPostRequest;

public interface ViewServices {
    View saveView(ViewPostRequest viewPostRequest);

    Long countNoOfViews();
}
