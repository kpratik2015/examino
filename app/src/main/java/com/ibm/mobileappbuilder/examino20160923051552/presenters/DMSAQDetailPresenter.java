
package com.ibm.mobileappbuilder.examino20160923051552.presenters;

import com.ibm.mobileappbuilder.examino20160923051552.R;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDSItem;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.mvp.view.DetailView;

public class DMSAQDetailPresenter extends BasePresenter implements DetailCrudPresenter<QuestionsDSItem>,
      Datasource.Listener<QuestionsDSItem> {

    private final CrudDatasource<QuestionsDSItem> datasource;
    private final DetailView view;

    public DMSAQDetailPresenter(CrudDatasource<QuestionsDSItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(QuestionsDSItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(QuestionsDSItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(QuestionsDSItem item) {
                view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
    }
}

