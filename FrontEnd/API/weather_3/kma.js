const SERVICE_KEY =
  "E8bVYmii5LsG9pBtYVXG%2FuaDeoLrlBeGMq4NpnZ9Hjoy9GF%2FqnzyG3qkYwj%2BVlgAcEc1OC04dvP0TnMyUPhq0Q%3D%3D";

getLocationAndThen((latitude, longitude) => {
  console.log(
    `환영합니다! 현재 위치는 위도 ${latitude}, 경도 ${longitude} 입니다.`
  );
  lat = latitude;
  lon = longitude;

  getWeather();

});

async function getWeather() {
  let lat = null;
  let lon = null;
  const resultDiv = document.getElementById("result");
  
  // const lat = parseFloat(document.getElementById('lat').value);
  // const lon = parseFloat(document.getElementById('lon').value);

  // const currentLocation = getGeoLocation();
  // setTimeout(getGeoLocation(), (location)=>{
  //         console.log('---->'+location.lat)
  //         const lat = currentLocation.latitude;
  //         const lon = currentLocation.longitude;
  //         console.log("-->" + currentLocation.latitude);
  //         const resultDiv = document.getElementById("result");
  //         document.getElementById("lat").value = lat;
  //         document.getElementById("lon").value = lon;

  // },2000);

  // if (isNaN(lat) || isNaN(lon)) {
  //   resultDiv.innerHTML = "❗ 위도와 경도를 올바르게 입력해주세요.";
  //   return;

  const { x, y } = dfs_xy_conv(lat, lon);

  const now = new Date();
  const baseDate = now.toISOString().slice(0, 10).replace(/-/g, "");
  const baseTime = "0500"; // 정시 기준 예시

  const url = `https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${SERVICE_KEY}&numOfRows=10&pageNo=1&dataType=JSON&base_date=${baseDate}&base_time=${baseTime}&nx=${x}&ny=${y}`;

  try {
    const res = await fetch(url);
    const json = await res.json();
    const items = json.response.body.items.item;

    const T1H = items.find((i) => i.category === "T1H");
    const SKY = items.find((i) => i.category === "SKY");
    const WSD = items.find((i) => i.category === "WSD");

    resultDiv.innerHTML = `
      <h3>날씨 정보</h3>
      <p>📍 위도: ${lat}, 경도: ${lon}</p>
      <p>🌡️ 기온: ${T1H?.fcstValue ?? "정보 없음"} °C</p>
      <p>☁️ 하늘 상태: ${skyToText(SKY?.fcstValue)}</p>
      <p>💨 풍속: ${WSD?.fcstValue ?? "정보 없음"} m/s</p>
    `;
  } catch (error) {
    resultDiv.innerHTML = `⚠️ API 호출 오류: ${error}`;
  }
}

function skyToText(code) {
  switch (code) {
    case "1":
      return "맑음";
    case "3":
      return "구름 많음";
    case "4":
      return "흐림";
    default:
      return "정보 없음";
  }
}

function getLocationAndThen(callback, delay = 1000) {
  if (!navigator.geolocation) {
    console.error("이 브라우저에서는 Geolocation API를 지원하지 않습니다.");
    return;
  }

  console.log("위치 정보를 요청 중입니다...");

  navigator.geolocation.getCurrentPosition(
    (position) => {
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;

      console.log(
        `위치 정보를 가져왔습니다: 위도=${latitude}, 경도=${longitude}`
      );

      // 지정된 시간 후 다음 작업 실행
      setTimeout(() => {
        console.log("지연 후 다음 작업 실행 중...");
        callback(latitude, longitude); // 위치를 인자로 다음 함수 호출
      }, delay);
    },
    (error) => {
      console.error("위치 정보 가져오기 실패:", error.message);
    },
    {
      enableHighAccuracy: true, // 가능한 경우 Wi-Fi 등 고정밀 위치 사용
      timeout: 10000,
      maximumAge: 0,
    }
  );
}

// 예시: 위치를 가져온 후 콘솔에 인사 메시지를 출력
// getLocationAndThen((lat, lon) => {
//   console.log(`환영합니다! 현재 위치는 위도 ${lat}, 경도 ${lon} 입니다.`);
// });

function getGeoLocation() {
  const location = {};

  if ("geolocation" in navigator) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude;
        const long = position.coords.longitude;
        console.log("==>" + lat);
        location.latitude = lat;
        location.longitude = long;
      },
      (error) => {
        console.error("위치 정보를 가져오는 데 실패했습니다.", error);
      }
    );
  } else {
    console.log("이 브라우저에서는 위치 정보 기능을 지원하지 않습니다.");
  }
  return location;
}
