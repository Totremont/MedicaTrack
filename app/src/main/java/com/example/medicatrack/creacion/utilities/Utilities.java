package com.example.medicatrack.creacion.utilities;

import android.text.Editable;
import android.text.TextWatcher;

public class Utilities {
    public abstract static class TextWatcherExtender implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public abstract void afterTextChanged(Editable editable);
    }
}
