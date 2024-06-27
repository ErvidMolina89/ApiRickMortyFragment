package com.wposs.apirickmortyfragment.Utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.wposs.apirickmortyfragment.R;

public class DialogueGenerico extends DialogFragment {

    private static DialogueGenerico instance;

    private View containerPrincipal;
    private ImageView image_dialog;
    private TextView details_message;
    private TextView title;
    private Button btn_Accept;
    private Button btn_Cancel;
    private Guideline guideLine;

    private Runnable invokeActionBtnAccept;
    private Runnable invokeActionBtnCancel;

    @StringRes
    private Integer routeTitle;
    @StringRes
    private Integer routeText;
    @StringRes
    private Integer routeTextBtnAccept;
    @StringRes
    private Integer routeTextBtnCancel;

    private TypeDialogue typeDialog = TypeDialogue.OK;

    private DialogueGenerico() {}

    public static DialogueGenerico getInstance() {
        if (instance == null) {
            instance = new DialogueGenerico();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        containerPrincipal = inflater.inflate(R.layout.dialogo_generico, container, false);
        setCancelable(false);

        findElementsView();
        loadViewInformation();
        return containerPrincipal;
    }

    private void findElementsView() {
        image_dialog = containerPrincipal.findViewById(R.id.image_dialog);
        details_message = containerPrincipal.findViewById(R.id.detail_message);
        title = containerPrincipal.findViewById(R.id.title_dialog);
        btn_Accept = containerPrincipal.findViewById(R.id.btn_accept_dialog);
        btn_Cancel = containerPrincipal.findViewById(R.id.btn_cancel_dialog);
        guideLine = containerPrincipal.findViewById(R.id.line_dialog);

        addListeners();
    }

    private void loadViewInformation() {
        selectIcon();
        introduceTitle();
        introduceMessage();
        introduceBtnAccept();
        introduceBtnCancel();
    }

    private void selectIcon() {
        if (typeDialog != null) {
            image_dialog.setImageResource(typeDialog.getIcono());
        }
    }

    private void introduceTitle() {
        if (routeTitle == null) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
            title.setText(routeTitle);
        }
    }

    private void introduceMessage() {
        if (routeText == null) {
            details_message.setVisibility(View.GONE);
        } else {
            details_message.setVisibility(View.VISIBLE);
            details_message.setText(routeText);
        }
    }

    private void introduceBtnAccept() {
        if (routeTextBtnAccept == null) {
            btn_Accept.setText(R.string.btn_aceptar);
        } else {
            btn_Accept.setText(routeTextBtnAccept);
        }
    }

    private void introduceBtnCancel() {
        if (routeTextBtnCancel == null) {
            btn_Cancel.setVisibility(View.GONE);
            guideLine.setGuidelinePercent(1.0f);
        } else {
            guideLine.setGuidelinePercent(0.5f);
            btn_Cancel.setVisibility(View.VISIBLE);
            btn_Cancel.setText(routeTextBtnCancel);
        }
    }

    private void addListeners() {
        btn_Accept.setOnClickListener(v -> {
            dismiss();
            if (invokeActionBtnAccept != null) {
                invokeActionBtnAccept.run();
            }
            clearViewElements();
        });
        btn_Cancel.setOnClickListener(v -> {
            dismiss();
            if (invokeActionBtnCancel != null) {
                invokeActionBtnCancel.run();
                clearViewElements();

            }
        });
    }

        private void clearViewElements() {
            routeTextBtnCancel = null;
            invokeActionBtnAccept = null;
            invokeActionBtnCancel = null;
        }

        @Override
        public void dismiss() {
            if (getFragmentManager() == null) {
                return;
            }
            super.dismiss();
        }

        @Override
        public void onStart() {
            super.onStart();
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }

        @Override
        public void show(@NonNull FragmentManager manager, String tag) {
            if (isAdded()) {
                return;
            }
            super.show(manager, tag);
        }

        public DialogueGenerico withActionBtnAccept(Runnable actionAccept) {
            this.invokeActionBtnAccept = actionAccept;
            return this;
        }

        public DialogueGenerico withActionBtnCancel(Runnable actionCancel) {
            this.invokeActionBtnCancel = actionCancel;
            return this;
        }

        public DialogueGenerico withTitle(@StringRes int routeString) {
            this.routeTitle = routeString;
            return this;
        }

        public DialogueGenerico withText(@StringRes int routeString) {
            this.routeText = routeString;
            return this;
        }

        public DialogueGenerico withTextBtnAccept(@StringRes int routeString) {
            this.routeTextBtnAccept = routeString;
            return this;
        }

        public DialogueGenerico withTextBtnCancel(@StringRes int routeString) {
            this.routeTextBtnCancel = routeString;
            return this;
        }

        public DialogueGenerico withTypeDialogue(TypeDialogue typeDialogue) {
            this.typeDialog = typeDialogue;
            return this;
        }

        public void showDialogue(FragmentManager fragmentManager, String tag) {
            show(fragmentManager, tag);
        }
}


