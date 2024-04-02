package africa.semicolon.services;

import africa.semicolon.data.models.View;
import africa.semicolon.data.repositories.ViewRepository;
import africa.semicolon.dto.requests.ViewPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.utils.Mapper.requestMap;

@Service
public class ViewServicesImpl implements ViewServices{

    @Autowired
    ViewRepository viewRepository;

    @Override
    public View saveView(ViewPostRequest viewPostRequest) {
        View view = requestMap(viewPostRequest);
        viewRepository.save(view);
        return view;
    }

    @Override
    public Long countNoOfViews() {
        return viewRepository.count();
    }
}
