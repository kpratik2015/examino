
package com.ibm.mobileappbuilder.examino20160923051552.ds;
import java.net.URL;
import com.ibm.mobileappbuilder.examino20160923051552.R;
import ibmmobileappbuilder.ds.RestService;
import ibmmobileappbuilder.util.StringUtils;

/**
 * "QuestioncountDSService" REST Service implementation
 */
public class QuestioncountDSService extends RestService<QuestioncountDSServiceRest>{

    public static QuestioncountDSService getInstance(){
          return new QuestioncountDSService();
    }

    private QuestioncountDSService() {
        super(QuestioncountDSServiceRest.class);

    }

    @Override
    public String getServerUrl() {
        return "https://ibm-pods.buildup.io";
    }

    @Override
    protected String getApiKey() {
        return "nq7WZggl";
    }

    @Override
    public URL getImageUrl(String path){
        return StringUtils.parseUrl("https://ibm-pods.buildup.io/app/57e4bbdee6c8040300ef29af",
                path,
                "apikey=nq7WZggl");
    }

}

