// Generated by view binder compiler. Do not edit!
package org.wit.placemark.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import org.wit.placemark.R;

public final class ActivityPlacemarkBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final Button btnAdd;

  @NonNull
  public final EditText placemarkDescription;

  @NonNull
  public final EditText placemarkTitle;

  @NonNull
  public final Toolbar toolbarAdd;

  private ActivityPlacemarkBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppBarLayout appBarLayout, @NonNull Button btnAdd,
      @NonNull EditText placemarkDescription, @NonNull EditText placemarkTitle,
      @NonNull Toolbar toolbarAdd) {
    this.rootView = rootView;
    this.appBarLayout = appBarLayout;
    this.btnAdd = btnAdd;
    this.placemarkDescription = placemarkDescription;
    this.placemarkTitle = placemarkTitle;
    this.toolbarAdd = toolbarAdd;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPlacemarkBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPlacemarkBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_placemark, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPlacemarkBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = ViewBindings.findChildViewById(rootView, id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.btnAdd;
      Button btnAdd = ViewBindings.findChildViewById(rootView, id);
      if (btnAdd == null) {
        break missingId;
      }

      id = R.id.placemarkDescription;
      EditText placemarkDescription = ViewBindings.findChildViewById(rootView, id);
      if (placemarkDescription == null) {
        break missingId;
      }

      id = R.id.placemarkTitle;
      EditText placemarkTitle = ViewBindings.findChildViewById(rootView, id);
      if (placemarkTitle == null) {
        break missingId;
      }

      id = R.id.toolbarAdd;
      Toolbar toolbarAdd = ViewBindings.findChildViewById(rootView, id);
      if (toolbarAdd == null) {
        break missingId;
      }

      return new ActivityPlacemarkBinding((ConstraintLayout) rootView, appBarLayout, btnAdd,
          placemarkDescription, placemarkTitle, toolbarAdd);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
