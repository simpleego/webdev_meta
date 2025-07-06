const API_KEY = '여기에_당신의_API_KEY를_입력하세요';

async function getWeather() {
  const city = document.getElementById('city').value;
  const resultDiv = document.getElementById('result');
  resultDiv.innerHTML = '⏳ 날씨 정보를 불러오는 중...';

  try {
    const res = await fetch(`https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}&units=metric&lang=kr`);
    const data = await res.json();

    if (res.ok) {
      const { main, weather, wind } = data;
      resultDiv.innerHTML = `
        <h2>${city}의 날씨</h2>
        <p>🌡️ 온도: ${main.temp} °C</p>
        <p>☁️ 상태: ${weather[0].description}</p>
        <p>💨 풍속: ${wind.speed} m/s</p>
      `;
    } else {
      resultDiv.innerHTML = `❌ ${data.message}`;
    }
  } catch (error) {
    resultDiv.innerHTML = `⚠️ 오류가 발생했습니다: ${error}`;
  }
}