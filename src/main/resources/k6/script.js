    import http from 'k6/http';
    import { sleep } from 'k6';

    export default function () {
      http.get('https://test.k6.io');
      sleep(1);
    }

    export const options = {
      scenarios: {
          point_charge: {
                executor: 'ramping-vus',
                startVUs: 10,
                stages: [
                  { duration: '10s', target: 50 }, // 10초 동안 50명까지 증가
                  { duration: '20s', target: 100 }, // 20초 동안 100명까지 유지
                  { duration: '10s', target: 0 }, // 10초 동안 감소
                ],
                gracefulStop: '5s',
              },
              coupon_receive: {
                executor: 'constant-vus',
                vus: 200, // 200명 동시 요청
                duration: '20s',
              },
              product_select: {
                executor: 'ramping-arrival-rate',
                preAllocatedVUs: 50,
                stages: [
                  { duration: '10s', target: 100 }, // 초당 100건 요청 증가
                  { duration: '30s', target: 200 }, // 초당 200건 유지
                ],
              },
              payment_process: {
                executor: 'constant-arrival-rate',
                rate: 10, // 초당 10건 요청
                timeUnit: '1s',
                duration: '30s',
                preAllocatedVUs: 20,
              },

    //    example_scenario: {
    //      // name of the executor to use
    //      executor: 'shared-iterations',
    //
    //      // common scenario configuration
    //      startTime: '10s',
    //      gracefulStop: '5s',
    //      env: { EXAMPLEVAR: 'testing' },
    //      tags: { example_tag: 'testing' },
    //
    //      // executor-specific configuration
    //      vus: 10,
    //      iterations: 200,
    //      maxDuration: '10s',
    //    },
    //    another_scenario: {
    //      /*...*/
    //    },
      },
    };

    const BASE_URL = 'http://host.docker.internal:8080/api/v1';
    export function customScenario() {
      let res;
      let scenario = __VU % 4; // 사용자별 테스트 분배

      if (scenario === 0) {
        res = http.post(`${BASE_URL}/point/charge/point/charge`, JSON.stringify({ userId: '123', amount: 1000 }), { headers: { 'Content-Type': 'application/json' } });
      } else if (scenario === 1) {
        res = http.post(`${BASE_URL}/coupons/receive`, JSON.stringify({ userId: '123', couponCode: 'NEWYEAR' }), { headers: { 'Content-Type': 'application/json' } });
      } else if (scenario === 2) {
        res = http.get(`${BASE_URL}/products/select`);
      } else {
        res = http.post(`${BASE_URL}/payment/`, JSON.stringify({ userId: '123', productId: 'A100', price: 5000 }), { headers: { 'Content-Type': 'application/json' } });
      }

      check(res, { 'status is 200': (r) => r.status === 200 });

      sleep(1);
    };