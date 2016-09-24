
package com.ibm.mobileappbuilder.examino20160923051552.ui;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDSItem;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDSService;
import com.ibm.mobileappbuilder.examino20160923051552.presenters.AllQuestionsFormPresenter;
import com.ibm.mobileappbuilder.examino20160923051552.R;
import ibmmobileappbuilder.ui.FormFragment;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.views.ImagePicker;
import ibmmobileappbuilder.views.TextWatcherAdapter;
import java.io.IOException;
import java.io.File;

import static android.net.Uri.fromFile;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDSItem;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDS;

public class QuestionsDSItemFormFragment extends FormFragment<QuestionsDSItem> {

    private CrudDatasource<QuestionsDSItem> datasource;

    public static QuestionsDSItemFormFragment newInstance(Bundle args){
        QuestionsDSItemFormFragment fr = new QuestionsDSItemFormFragment();
        fr.setArguments(args);

        return fr;
    }

    public QuestionsDSItemFormFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // the presenter for this view
        setPresenter(new AllQuestionsFormPresenter(
                (CrudDatasource) getDatasource(),
                this));

            }

    @Override
    protected QuestionsDSItem newItem() {
        return new QuestionsDSItem();
    }

    private QuestionsDSService getRestService(){
        return QuestionsDSService.getInstance();
    }

    @Override
    protected int getLayout() {
        return R.layout.allquestions_form;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final QuestionsDSItem item, View view) {
        
        bindString(R.id.questionsds_subject, item.subject, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.subject = s.toString();
            }
        });
        
        
        bindString(R.id.questionsds_type, item.type, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.type = s.toString();
            }
        });
        
        
        bindString(R.id.questionsds_questiontype, item.questionType, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.questionType = s.toString();
            }
        });
        
        
        bindString(R.id.questionsds_question, item.question, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.question = s.toString();
            }
        });
        
        
        bindImage(R.id.questionsds_figure,
            item.figure != null ?
                getRestService().getImageUrl(item.figure) : null,
            0,
            new ImagePicker.Callback(){
                @Override
                public void imageRemoved(){
                    item.figure = null;
                    item.figureUri = null;
                    ((ImagePicker) getView().findViewById(R.id.questionsds_figure)).clear();
                }
            }
        );
        
    }

    @Override
    public Datasource<QuestionsDSItem> getDatasource() {
      if (datasource != null) {
        return datasource;
      }
       datasource = QuestionsDS.getInstance(new SearchOptions());
        return datasource;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            ImagePicker picker = null;
            Uri imageUri = null;
            QuestionsDSItem item = getItem();

            if((requestCode & ImagePicker.GALLERY_REQUEST_CODE) == ImagePicker.GALLERY_REQUEST_CODE) {
              imageUri = data.getData();
              switch (requestCode - ImagePicker.GALLERY_REQUEST_CODE) {
                        
                        case 0:   // figure field
                            item.figureUri = imageUri;
                            item.figure = "cid:figure";
                            picker = (ImagePicker) getView().findViewById(R.id.questionsds_figure);
                            break;
                        
                default:
                  return;
              }

              picker.setImageUri(imageUri);
            } else if((requestCode & ImagePicker.CAPTURE_REQUEST_CODE) == ImagePicker.CAPTURE_REQUEST_CODE) {
				      switch (requestCode - ImagePicker.CAPTURE_REQUEST_CODE) {
                        
                        case 0:   // figure field
                            picker = (ImagePicker) getView().findViewById(R.id.questionsds_figure);
                            imageUri = fromFile(picker.getImageFile());
                        		item.figureUri = imageUri;
                            item.figure = "cid:figure";
                            break;
                        
                default:
                  return;
              }
              picker.setImageUri(imageUri);
            }
        }
    }
}

