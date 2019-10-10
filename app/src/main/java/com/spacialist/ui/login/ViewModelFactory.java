package com.spacialist.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.spacialist.ui.main.BookViewModel;
import com.spacialist.ui.nav.HistoryViewModel;
import com.spacialist.ui.main.NotificationViewModel;
import com.spacialist.ui.main.PaymentViewModel;
import com.spacialist.ui.main.ServiceViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel();
        } else if (modelClass.isAssignableFrom(BookViewModel.class)) {
            return (T) new BookViewModel();
        } else if (modelClass.isAssignableFrom(ServiceViewModel.class)) {
            return (T) new ServiceViewModel();
        } else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel();
        } else if (modelClass.isAssignableFrom(PaymentViewModel.class)) {
            return (T) new PaymentViewModel();
        } else if (modelClass.isAssignableFrom(NotificationViewModel.class)) {
            return (T) new NotificationViewModel();
        } else if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            return (T) new HistoryViewModel();
        } else if (modelClass.isAssignableFrom(EditProfileViewModel.class)) {
            return (T) new EditProfileViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
