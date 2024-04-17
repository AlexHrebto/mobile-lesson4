package ru.mirea.khrebtovsky.data_thread;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import ru.mirea.khrebtovsky.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn3");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    binding.tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}

// ОТВЕТЫ НА ВОПРОСЫ
// Методы runOnUiThread, postDelayed и post в Android используются для выполнения кода на главном потоке UI (UI thread) из фонового потока (background thread). Они позволяют вам обновлять пользовательский интерфейс в ответ на события или задержки.
//
// runOnUiThread(Runnable action): Этот метод позволяет запускать Runnable кода на главном потоке UI. Код, переданный в runOnUiThread, будет выполнен немедленно, если он уже выполняется на главном потоке, иначе он будет помещен в очередь выполнения для главного потока и выполнен, когда будет возможность.
//
// postDelayed(Runnable action, long delayMillis): Этот метод позволяет запланировать выполнение Runnable кода через определенное количество времени (в миллисекундах). Код будет выполнен на потоке, к которому привязан View, но он будет отложен до истечения указанной задержки.
//
// post(Runnable action): Этот метод позволяет добавить Runnable кода в очередь выполнения для View, но выполнение кода начнется, как только будет возможность. Он гарантирует, что код будет выполнен на потоке, к которому привязана View, но не гарантирует, что он будет выполнен сразу.
//
// android:maxLines="10" указывает максимальное количество строк, которые могут отображаться в TextView. Если текст в TextView занимает больше строк, чем указано в maxLines, он будет обрезан или скрыт, и вы можете прокручивать его.
// android:lines="10" устанавливает точное количество строк, которые будут отображаться в TextView. Если текст не вмещается в это количество строк, то он будет обрезан или скрыт, и вы можете прокручивать его.
//
// Теперь, если рассмотреть последовательность запуска процессов:
//
// Код, переданный в runOnUiThread, выполнится сразу на главном потоке UI.
// Код, переданный в postDelayed, будет отложен на указанное количество времени и затем выполнится на главном потоке UI.
// Код, переданный в post, будет добавлен в очередь выполнения для View и выполнится, когда будет доступен главный поток UI.