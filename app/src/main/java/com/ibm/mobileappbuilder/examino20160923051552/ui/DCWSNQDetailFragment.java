
package com.ibm.mobileappbuilder.examino20160923051552.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDSService;
import com.ibm.mobileappbuilder.examino20160923051552.presenters.DCWSNQDetailPresenter;
import com.ibm.mobileappbuilder.examino20160923051552.R;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.behaviors.ShareBehavior;
import ibmmobileappbuilder.ds.restds.AppNowDatasource;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.image.ImageLoader;
import ibmmobileappbuilder.util.image.PicassoImageLoader;
import ibmmobileappbuilder.util.StringUtils;
import java.net.URL;
import static ibmmobileappbuilder.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDSItem;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDS;

public class DCWSNQDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<QuestionsDSItem> implements ShareBehavior.ShareListener  {

    private CrudDatasource<QuestionsDSItem> datasource;
    public static DCWSNQDetailFragment newInstance(Bundle args){
        DCWSNQDetailFragment fr = new DCWSNQDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public DCWSNQDetailFragment(){
        super();
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
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // the presenter for this view
        setPresenter(new DCWSNQDetailPresenter(
                (CrudDatasource) getDatasource(),
                this));
        // Edit button
        addBehavior(new FabBehaviour(this, R.drawable.ic_edit_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailCrudPresenter<QuestionsDSItem>) getPresenter()).editForm(getItem());
            }
        }));
        addBehavior(new ShareBehavior(getActivity(), this));

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.dcwsnqdetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final QuestionsDSItem item, View view) {
        if (item.subject != null){
            
            TextView view0 = (TextView) view.findViewById(R.id.view0);
            view0.setText(item.subject);
            
        }
        if (item.type != null){
            
            TextView view1 = (TextView) view.findViewById(R.id.view1);
            view1.setText(item.type);
            
        }
        if (item.questionType != null){
            
            TextView view2 = (TextView) view.findViewById(R.id.view2);
            view2.setText(item.questionType);
            
        }
        if (item.question != null){
            
            TextView view3 = (TextView) view.findViewById(R.id.view3);
            view3.setText(item.question);
            
        }
        
        ImageView view4 = (ImageView) view.findViewById(R.id.view4);
        URL view4Media = ((AppNowDatasource) getDatasource()).getImageUrl(item.figure);
        if(view4Media != null){
          ImageLoader imageLoader = new PicassoImageLoader(view4.getContext());
          imageLoader.load(imageLoaderRequest()
                                   .withPath(view4Media.toExternalForm())
                                   .withTargetView(view4)
                                   .fit()
                                   .build()
                    );
        	
        } else {
          view4.setImageDrawable(null);
        }
    }

    @Override
    protected void onShow(QuestionsDSItem item) {
        // set the title for this fragment
        getActivity().setTitle("DCWSN Q.");
    }

    @Override
    public void navigateToEditForm() {
        Bundle args = new Bundle();

        args.putInt(Constants.ITEMPOS, 0);
        args.putParcelable(Constants.CONTENT, getItem());
        args.putInt(Constants.MODE, Constants.MODE_EDIT);

        Intent intent = new Intent(getActivity(), QuestionsDSItemFormActivity.class);
        intent.putExtras(args);
        startActivityForResult(intent, Constants.MODE_EDIT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_menu, menu);

        MenuItem item = menu.findItem(R.id.action_delete);
        ColorUtils.tintIcon(item, R.color.textBarColor, getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            ((DetailCrudPresenter<QuestionsDSItem>) getPresenter()).deleteItem(getItem());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onShare() {
        QuestionsDSItem item = getItem();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, (item.subject != null ? item.subject : "" ) + "\n" +
                    (item.type != null ? item.type : "" ) + "\n" +
                    (item.questionType != null ? item.questionType : "" ) + "\n" +
                    (item.question != null ? item.question : "" ));
        intent.putExtra(Intent.EXTRA_SUBJECT, "DCWSN Q.");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.share)), 1);
    }
}

