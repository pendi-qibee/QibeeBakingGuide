package id.qibee.edu.qibeebakingguide.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import id.qibee.edu.qibeebakingguide.model.StepModel;
import id.qibee.edu.qibeebakingguide.view.RecipeStepFragment2;

/**
 * Created by masq2 on 16/09/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return "Step " + ++position;
    }

    private List<StepModel> stepModelList;

    //Constructor,
    public ViewPagerAdapter(FragmentManager fm, List<StepModel> stepModelList) {
        super(fm);
        this.stepModelList = stepModelList;
    }

    @Override
    public Fragment getItem(int position) {
//        return null;
        return RecipeStepFragment2.newInstance(stepModelList.get(position));
    }

    @Override
    public int getCount() {
//        return 0;
        return stepModelList.size();
    }
}
