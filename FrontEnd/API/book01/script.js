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
  .then(res => res.json())
  .then(data => {
    const books = data.documents;
    if (books.length === 0) {
      resultsDiv.innerHTML = "😢 결과가 없습니다.";
      return;
    }

    resultsDiv.innerHTML = books.map((book) => `
      <div style="margin-bottom: 20px; border-bottom: 1px solid #ccc; padding-bottom: 10px;">
        <img src="${book.thumbnail}" alt="책 표지" style="float:left; margin-right:15px; width:80px; cursor:pointer"
            onclick='openModal(${JSON.stringify(book).replace(/'/g, "\\'")})' />
        <strong>${book.title}</strong><br />
        저자: ${book.authors.join(", ")}<br />
        출판사: ${book.publisher}<br />
        <small>출판일: ${book.datetime.split("T")[0]}</small><br />
        <a href="#" onclick='openTOCModal(${JSON.stringify(book).replace(/'/g, "\\'")}); return false;'>📖 목차보기</a>
        <div style="clear:both;"></div>
      </div>`
    ).join("");


    // resultsDiv.innerHTML = books.map((book, index) => `
    //   <div style="margin-bottom: 20px; border-bottom: 1px solid #ccc; padding-bottom: 10px;">
    //     <img src="${book.thumbnail}" alt="책 표지" style="float:left; margin-right:15px; width:80px; cursor:pointer"
    //          onclick='openModal(${JSON.stringify(book).replace(/'/g, "\\'")})' />
    //     <strong>${book.title}</strong><br />
    //     저자: ${book.authors.join(", ")}<br />
    //     출판사: ${book.publisher}<br />
    //     <small>출판일: ${book.datetime.split("T")[0]}</small>
    //     <div style="clear:both;"></div>
    //   </div>
    // `).join("");

  })
  .catch(err => {
    console.error(err);
    resultsDiv.innerHTML = "오류가 발생했습니다.";
  });
}

function openModal(book) {
  const modal = document.getElementById("modal");
  const body = document.getElementById("modal-body");
  modal.style.display = "block";

  body.innerHTML = `
    <h2>${book.title}</h2>
    <img src="${book.thumbnail}" style="width:120px; float:left; margin-right:20px;" />
    <p><strong>저자:</strong> ${book.authors.join(", ")}</p>
    <p><strong>출판사:</strong> ${book.publisher}</p>
    <p><strong>ISBN:</strong> ${book.isbn}</p>
    <p><strong>가격:</strong> ${book.price}원</p>
    <p style="clear:both;">${book.contents}</p>
    <a href="${book.url}" target="_blank">📘 자세히 보기 (Kakao 책 페이지)</a>
  `;
}

function openTOCModal(book) {
  const tocModal = document.getElementById("toc-modal");
  const tocBody = document.getElementById("toc-body");

  tocModal.style.display = "block";

  tocBody.innerHTML = `
    <h2>${book.title} - 목차</h2>
    <p>${book.contents || "📌 제공된 목차 정보가 없습니다."}</p>
    <a href="${book.url}" target="_blank">📘 Kakao 책 상세 보기</a>
  `;
}

function closeTOCModal() {
  document.getElementById("toc-modal").style.display = "none";
}

function closeModal() {
  document.getElementById("modal").style.display = "none";
}