package com.example.pr26;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFY_ID = 123;
    private int counter = 123;
    private static String CHANNEL_ID = "MEOW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        createNotificationChannel();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("MissingPermission")
            public void onClick(View v) {
                long[] vibrate = new long[] { 1000, 1000, 1000, 1000, 1000 };

                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                //Создаем ожидающее намерение - то которое будет выполнено позже
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                String bigText = "meoweowmeowmeowmeowmeowmeowmeowmeowmeowemwoemwemmwemwemwe.";

                Person scottish = new Person.Builder().setName("Скоттиш").build();
                Person shugga = new Person.Builder().setName("Шугга").build();

                //создание стиля общения
                NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(scottish)
                        .setConversationTitle("MewChat")
                        .addMessage("Привет котаны!", System.currentTimeMillis(), scottish)
                        .addMessage("А вы знали, что chat по-французски кошка?", System.currentTimeMillis(), scottish)
                        .addMessage("Круто!", System.currentTimeMillis(), shugga)
                        .addMessage("ми-ми-ми", System.currentTimeMillis(), shugga)
                        .addMessage("Скоттиш, откуда ты знаешь французский?", System.currentTimeMillis(), shugga)
                        .addMessage("Cherchez la femme, т.е. ищите кошечку!", System.currentTimeMillis(), scottish);

                //создание уведомления
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.baseline_pets_24)
                                .setContentTitle("Уведомление")
                                .setContentText("Пора отдохнуть")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setContentIntent(contentIntent)
                                .setAutoCancel(true)
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setVibrate(vibrate)
                                .setLights(Color.RED,0,1)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                                .setStyle(messagingStyle)
                                .setColor(Color.GREEN);

                // Класс NotificationManagerCompat используется для управления уведомлениями.
                // Метод from() используется для получения ссылки на менеджер уведомлений для текущего активити.
                // Метод notify() используется для отображения уведомления.
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(NOTIFY_ID, builder.build());
            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MEOW"; //уст имя канала
            String description = "Channel for meow notifications"; //описание канала
            int importance = NotificationManager.IMPORTANCE_HIGH; //устанавливает важность канала
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);//созд нового канала
            channel.setDescription(description);
            //получает ссылку на менеджер уведомлений для текущего активити.
            // Менеджер уведомлений отвечает за управление уведомлениями на устройстве.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            //создаем канал
            notificationManager.createNotificationChannel(channel);
        }
    }
}