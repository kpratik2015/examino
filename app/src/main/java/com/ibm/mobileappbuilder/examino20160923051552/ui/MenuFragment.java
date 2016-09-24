

package com.ibm.mobileappbuilder.examino20160923051552.ui;

import android.os.Bundle;

import com.ibm.mobileappbuilder.examino20160923051552.R;

import java.util.ArrayList;
import java.util.List;

import ibmmobileappbuilder.MenuItem;

import ibmmobileappbuilder.actions.StartActivityAction;
import ibmmobileappbuilder.util.Constants;

/**
 * MenuFragment menu fragment.
 */
public class MenuFragment extends ibmmobileappbuilder.ui.MenuFragment {

    /**
     * Default constructor
     */
    public MenuFragment(){
        super();
    }

    // Factory method
    public static MenuFragment newInstance(Bundle args) {
        MenuFragment fragment = new MenuFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
      public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
                }

    // Menu Fragment interface
    @Override
    public List<MenuItem> getMenuItems() {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
        items.add(new MenuItem()
            .setLabel("All Questions")
            .setIcon(R.drawable.jpg_all897)
            .setAction(new StartActivityAction(AllQuestionsActivity.class, Constants.DETAIL))
        );
        items.add(new MenuItem()
            .setLabel("CFCA Q.")
            .setIcon(R.drawable.jpg_cfca400)
            .setAction(new StartActivityAction(CFCAQMenuItem1Activity.class, Constants.DETAIL))
        );
        items.add(new MenuItem()
            .setLabel("DMSA Q.")
            .setIcon(R.drawable.jpg_dmsa980)
            .setAction(new StartActivityAction(DMSAQActivity.class, Constants.DETAIL))
        );
        items.add(new MenuItem()
            .setLabel("DCWSN Q.")
            .setIcon(R.drawable.jpg_dcwsn65)
            .setAction(new StartActivityAction(DCWSNQActivity.class, Constants.DETAIL))
        );
        items.add(new MenuItem()
            .setLabel("Questions Count")
            .setIcon(R.drawable.png_bwstacks475)
            .setAction(new StartActivityAction(QuestionsCountActivity.class, Constants.DETAIL))
        );
        return items;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_grid;
    }

    @Override
    public int getItemLayout() {
        return R.layout.menu_item;
    }
}

