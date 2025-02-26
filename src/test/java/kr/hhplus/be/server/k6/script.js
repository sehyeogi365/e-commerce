import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 10,
  duration: '10s',

  ext: {
      influxdb: {
        url: 'http://localhost:8086',  // InfluxDB 주소
        database: 'testdb',            // 사용할 데이터베이스
          precision: 's',
      },
    },
};

export default function () {
  http.get('https://test.k6.io');
  sleep(1);
}