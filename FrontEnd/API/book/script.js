const REST_API_KEY = 'a0bdb6661fd328889edbc0cd1ed902c2'; // 👈 여기에 본인의 Kakao REST API 키를 입력하세요

function searchBooks() {
  const query = document.getElementById("query").value.trim();
  const resultsDiv = document.getElementById("results");
  resultsDiv.innerHTML = "🔍 검색 중...";

  fetch(`https://dapi.kakao.com/v3/search/book?target=title&query=${encodeURIComponent(query)}`, {
    headers: {
     Authorization: `KakaoAK ${REST_API_KEY}`
    }
  })
  .then(response => response.json())
  .then(data => {
    const books = data.documents;
    if (books.length === 0) {
      resultsDiv.innerHTML = "😢 결과가 없습니다.";
      return;
    }

    resultsDiv.innerHTML = books.map(book => `
      <div style="margin-bottom: 20px; border-bottom: 1px solid #ccc; padding-bottom: 10px;">
        <img src="${book.thumbnail}" alt="책 표지" style="float:left; margin-right:15px; width:80px;" />
        <strong>${book.title}</strong><br />
        저자: ${book.authors.join(", ")}<br />
        출판사: ${book.publisher}<br />
        <small>출판일: ${book.datetime.split("T")[0]}</small>
        <div style="clear:both;"></div>
      </div>
    `).join("");
  })
  .catch(err => {
    console.error(err);
    resultsDiv.innerHTML = "오류가 발생했습니다.";
  });
}
