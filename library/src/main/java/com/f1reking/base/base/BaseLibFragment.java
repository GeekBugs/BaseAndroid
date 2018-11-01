package com.f1reking.base.base;

import androidx.fragment.app.Fragment;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author F1ReKing
 * @date 2018/11/1 15:58
 * @Description baseFragment
 */
public class BaseLibFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseLibApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
