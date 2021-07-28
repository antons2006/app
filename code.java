public class MainActivity extends AppCompatActivity implements View.OnTouchListener - на это замените ваш класс

//показ иконки, можно в onCreate вставить
   windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

                final LayoutParams myParams = new WindowManager.LayoutParams(
                        200, // Ширина экрана
                        200, // Высота экрана
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Говорим, что приложение будет поверх других. В поздних API > 26, данный флаг перенесен на TYPE_APPLICATION_OVERLAY
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // Необходимо для того чтобы TouchEvent'ы в пустой области передавались на другие приложения
                        PixelFormat.TRANSLUCENT); // Само окно прозрачное
                ((WindowManager.LayoutParams) myParams).gravity = Gravity.TOP | Gravity.LEFT;
                ((WindowManager.LayoutParams) myParams).x=0;
                ((WindowManager.LayoutParams) myParams).y=100;
                // add a floatingfacebubble icon in window
                rootView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.clickerr, null); 
                s =  rootView.findViewById(R.id.imageView6);
                rootView.setOnTouchListener(this);
                windowManager.addView(rootView, myParams);
                topLeftView = new View(this);
                WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
                topLeftParams.gravity = Gravity.LEFT | Gravity.TOP;
                topLeftParams.x = 0;
                topLeftParams.y = 0;
                topLeftParams.width = 0;
                topLeftParams.height = 0;
                windowManager.addView(topLeftView, topLeftParams);

//это метод что бы двигать иконку
 @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getRawX();
            float y = event.getRawY();

            moving = false;

            int[] location = new int[2];
            rootView.getLocationOnScreen(location);

            originalXPos = location[0];
            originalYPos = location[1];

            offsetX = originalXPos - x;
            offsetY = originalYPos - y;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int[] topLeftLocationOnScreen = new int[2];

            topLeftView.getLocationOnScreen(topLeftLocationOnScreen);



            float x = event.getRawX();
            float y = event.getRawY();

            WindowManager.LayoutParams params = (WindowManager.LayoutParams) rootView.getLayoutParams();

            int newX = (int) (offsetX + x);
            int newY = (int) (offsetY + y);

            if (Math.abs(newX - originalXPos) < 1 && Math.abs(newY - originalYPos) < 1 && !moving) {
                return false;
            }

            params.x = newX - (topLeftLocationOnScreen[0]);
            params.y = newY - (topLeftLocationOnScreen[1]);

            windowManager.updateViewLayout(rootView, params);
            moving = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (moving) {
                return true;
            }
        }

        return false;
    }

//все переменные что юзал там
    View topLeftView;
    ImageView s;
    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private boolean moving;
    RelativeLayout rootView;
    private WindowManager windowManager;
