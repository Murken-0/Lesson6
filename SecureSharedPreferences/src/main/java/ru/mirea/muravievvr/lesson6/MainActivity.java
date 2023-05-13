package ru.mirea.muravievvr.lesson6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.view.View;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.muravievvr.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    SharedPreferences secureSharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        try {
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            String result = secureSharedPreferences.getString("secure", "unknown");
            String foto = secureSharedPreferences.getString("foto", "unknown");
            binding.editTextName.setText(result);
            binding.editTextFile.setText(foto);

            binding.ImageView.setImageResource(getResources().getIdentifier(foto,"drawable", getPackageName()));
        }
        catch (GeneralSecurityException | IOException e)
        { throw new RuntimeException(e);
        }
        binding.button.setOnClickListener( v -> {
            secureSharedPreferences.edit().putString("secure", binding.editTextName.getText().toString()).apply();
            secureSharedPreferences.edit().putString("foto", binding.editTextFile.getText().toString()).apply();
        });
    }
}