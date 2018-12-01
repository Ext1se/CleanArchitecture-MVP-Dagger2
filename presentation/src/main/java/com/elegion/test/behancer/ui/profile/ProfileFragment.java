package com.elegion.test.behancer.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.common.PresenterFragment;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.ponomarevigor.data.model.user.User;
import com.elegion.test.behancer.di.module.ProfileFragmentModule;
import com.elegion.test.behancer.utils.DateUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Named;

public class ProfileFragment extends PresenterFragment implements ProfileView, Refreshable {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private View mErrorView;
    private View mProfileView;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;

    @Inject
    RefreshOwner mRefreshOwner;
    @InjectPresenter
    ProfilePresenter mPresenter;
    @Inject
    @Named(PROFILE_KEY)
    String mUsername;

    @ProvidePresenter
    ProfilePresenter providePresenter() {
        return new ProfilePresenter();
    }

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void injectDependencies() {
        AppDelegate
                .getAppComponent()
                .plusProfileFragment(new ProfileFragmentModule(this))
                .inject(this);
    }

    @Inject
    @Override
    public void setDependencies() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mProfileView = view.findViewById(R.id.view_profile);

        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }

        if (savedInstanceState == null) {
            onRefreshData();
        }
    }

    @Override
    public void onRefreshData() {
        mPresenter.getProfile(mUsername);
    }

    private void bind(User user) {
        Picasso.get()
                .load(user.getImage().getPhotoUrl())
                .fit()
                .error(R.drawable.ic_warning)
                .into(mProfileImage);

        mProfileName.setText(user.getDisplayName());
        mProfileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.setText(user.getLocation());
    }

    @Override
    protected ProfilePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDetach() {
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showProfile(User user) {
        mErrorView.setVisibility(View.GONE);
        mProfileView.setVisibility(View.VISIBLE);
        bind(user);
    }

    @Override
    public void showRefresh() {
        if (mRefreshOwner != null) {
            mRefreshOwner.setRefreshState(true);
        }
    }

    @Override
    public void hideRefresh() {
        if (mRefreshOwner != null) {
            mRefreshOwner.setRefreshState(false);
        }
    }

    @Override
    public void showMessage(boolean networkConnection) {
        if (!networkConnection) {
            Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mProfileView.setVisibility(View.GONE);
    }
}
