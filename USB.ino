#include <SoftwareSerial.h>
#include <string.h>
#include <Keypad.h>

#define Init_row 8
#define Init_col 8
#define MAX_ROWS 3
#define MAX_COLS 6

const byte ROWS = 4;    // 행(rows) 개수
const byte COLS = 4;    // 열(columns) 개수
char keys[ROWS][COLS] = {
  {' ','0','3','*'},
  {' ','1','4','$'},
  {' ','2','5','#'},
  {' ','A','B','@'}
};

// 1개 올리기: *
// 1개 지우기: $
// 전체 지우기: #
// 제출하기: @
// 현재 초기화: B

byte rowPins[ROWS] = {A2, A1, A0, 13};   // R1, R2, R3, R4 단자가 연결된 아두이노 핀 번호
byte colPins[COLS] = {0, A3, A4, A5};   // C1, C2, C3, C4 단자가 연결된 아두이노 핀 번호

Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );

int rows[] = {5, 7, 0, 8, 10, 0, 9, 12};
int cols[] = {0, 0, 0, 4, 11, 6, 0, 0};
int xy[3][2] = {{5, 0}, {5, 3}, {5, 6}};

SoftwareSerial bt(2,3);

int Init[Init_row][Init_col] = {0, };

void clear(){
  for(int i = 0; i < 8; i++){
    digitalWrite(rows[i],LOW);
    digitalWrite(cols[i],HIGH);
  }
}

void setup() {
  for(int i = 0; i < 8; i++){
    pinMode(rows[i], OUTPUT);
    pinMode(cols[i], OUTPUT);
  }
  Serial.begin(9600);
  bt.begin(9600);
}

//array paste to Init
void paste(int Init[Init_row][Init_col], int arr[MAX_COLS], int x, int y) {
    int cnt = 0;
    for(int i = 0; i < 3; i++) {
        if(arr[i] == 0) {
          Init[y][x-cnt] = 0;
        }
        else if(arr[i] == 1) {
          Init[y][x-cnt] = 1;
        }
        cnt++;
    }
    cnt = 0;
    for(int i = 3; i < 6; i++) {
        if(arr[i] == 0) {
          Init[y+1][x-cnt] = 0;
        }
        else if(arr[i] == 1) {
          Init[y+1][x-cnt] = 1;
        }
        cnt++;
    }
}

//create array
void createArray(int arr[MAX_ROWS][MAX_COLS], char* str, int row, int col) {
    char* token = strtok(str, "[], ");
    token = strtok(NULL, "[], ");
    for(int i = 0; i < row; i++) {
        for(int j = 0; j < col; j++) {
            arr[i][j] = atoi(token);
            token = strtok(NULL, "[], ");
        }
    }
}

String receivedData = "";
String send_str = "000000";

//bluetooth loop
void loop() {
  char key = keypad.getKey();

  if(bt.available()){
    char data = bt.read();

    //data receive
    if(data == ';') {
      for(int i = 0; i < Init_row; i++) {
        for(int j = 0; j < Init_col; j++) {
          if(Init[i][j] == 1) {
            Init[i][j] = 0;
          }
        }
      }

      char* braille = const_cast<char*>(receivedData.c_str());

      int row = braille[0] - '0';
      int col = MAX_COLS;

      int arr[MAX_ROWS][MAX_COLS] = {0, };
      createArray(arr, braille, row, col);

      //arr print
      // Serial.print("arr: ");
      // for(int i = 0; i < row; i++) {
      //   for(int j = 0; j < col; j++) {
      //     Serial.print(arr[i][j]);
      //   }
      //   Serial.print('/');
      // }

      //Init에 붙여넣기
      int cnt = 0;
      for(int i = 0; i < row; i++) {
          paste(Init, arr[i], xy[i][0], xy[i][1]);
      }

      //Init print
      // Serial.print("Init: ");
      // for(int i = 0; i < Init_row; i++) {
      //   for(int j = 0; j < Init_col; j++) {
      //     Serial.print(Init[i][j]);
      //   }
      //   Serial.print('/');
      // }

      receivedData = "";
    }
    else {
      receivedData += data;
    }
  }
  if(Serial.available()){
    bt.write(Serial.read());
  }
  
  for(int x = 0; x < Init_row; x++){
    clear();
    digitalWrite(rows[x], HIGH);
    for(int y = 0; y < Init_col; y++){
      if(Init[x][y] == 1)
        digitalWrite(cols[y], LOW);
    }
    delay(1);
  }

  if(key){
    switch(key) {
      case '0':
        send_str[0] = '1';
        break;
      case '1':
        send_str[1] = '1';
        break;
      case '2':
        send_str[2] = '1';
        break;
      case '3':
        send_str[3] = '1';
        break;
      case '4':
        send_str[4] = '1';
        break;
      case '5':
        send_str[5] = '1';
        break;
      // 1개 올리기: *
      case '*':
        send_str = send_str + ';';  
        bt.print(send_str);
        send_str = "000000";
        break;
      // 1개 지우기: $
      case '$':
        bt.print("$;");
        break;
      // 전체 지우기: #
      case '#':
        bt.print("#;");
        break;
      // 제출하기: @
      case '@':
        bt.print("@;");
        break;
      // 현재 초기화: B
      case 'B':
        send_str = "000000";
        break; 
      case 'A':
        Init[0][3] = 1;
        Init[0][5] = 1;
        Init[1][3] = 1;
        Init[3][3] = 1;
        Init[3][4] = 1;
        Init[4][5] = 1;
        Init[6][4] = 1;
        Init[6][5] = 1;
        break;
      default:
        break;
    }
  }
}

