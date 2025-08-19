```html

<!-- ========================= 1. 메인 페이지 (index.jsp) ========================= -->
<!-- 파일위치: src/main/webapp/WEB-INF/views/index.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle}</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .navbar { box-shadow: 0 2px 4px rgba(0,0,0,.1); }
        .main-content { margin-top: 2rem; margin-bottom: 2rem; }
        .card { border: none; border-radius: 10px; box-shadow: 0 0 20px rgba(0,0,0,.1); }
        .card:hover { transform: translateY(-5px); transition: transform 0.2s; }
        .card-header { 
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 10px 10px 0 0 !important;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
    </style>
</head>
<body>
    <!-- 네비게이션 -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/' />">
                <i class="fas fa-book"></i> 서점 관리 시스템
            </a>
            <div class="navbar-nav">
                <a class="nav-link active" href="<c:url value='/' />">홈</a>
                <a class="nav-link" href="<c:url value='/books/list' />">도서 관리</a>
                <a class="nav-link" href="#">고객 관리</a>
                <a class="nav-link" href="#">주문 관리</a>
            </div>
        </div>
    </nav>

    <!-- 메인 컨텐츠 -->
    <div class="container main-content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <h2 class="mb-0"><i class="fas fa-home"></i> 서점 관리 시스템</h2>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <div class="card h-100 border-primary">
                                    <div class="card-body text-center">
                                        <i class="fas fa-book fa-3x text-primary mb-3"></i>
                                        <h5 class="card-title">도서 관리</h5>
                                        <p class="card-text">도서 등록, 수정, 삭제 및 조회 기능</p>
                                        <a href="<c:url value='/books/list' />" class="btn btn-primary">
                                            <i class="fas fa-arrow-right"></i> 도서 목록 보기
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 mb-3">
                                <div class="card h-100 border-success">
                                    <div class="card-body text-center">
                                        <i class="fas fa-users fa-3x text-success mb-3"></i>
                                        <h5 class="card-title">고객 관리</h5>
                                        <p class="card-text">고객 정보 관리 기능</p>
                                        <a href="#" class="btn btn-success">고객 목록 보기</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 mb-3">
                                <div class="card h-100 border-warning">
                                    <div class="card-body text-center">
                                        <i class="fas fa-shopping-cart fa-3x text-warning mb-3"></i>
                                        <h5 class="card-title">주문 관리</h5>
                                        <p class="card-text">주문 처리 및 관리 기능</p>
                                        <a href="#" class="btn btn-warning">주문 목록 보기</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<!-- ========================= 2. 도서 목록 페이지 (book/list.jsp) ========================= -->
<!-- 파일위치: src/main/webapp/WEB-INF/views/book/list.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>도서 목록 - 서점 관리 시스템</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .navbar { box-shadow: 0 2px 4px rgba(0,0,0,.1); }
        .main-content { margin-top: 2rem; margin-bottom: 2rem; }
        .card { 
            border: none; 
            border-radius: 10px; 
            box-shadow: 0 0 20px rgba(0,0,0,.1); 
        }
        .card-header { 
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 10px 10px 0 0 !important;
        }
        .table th { 
            background-color: #495057; 
            color: white; 
            border: none; 
        }
        .table td { vertical-align: middle; }
        .price { font-weight: bold; color: #28a745; }
        .btn-primary { 
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
    </style>
</head>
<body>
    <!-- 네비게이션 -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/' />">
                <i class="fas fa-book"></i> 서점 관리 시스템
            </a>
            <div class="navbar-nav">
                <a class="nav-link" href="<c:url value='/' />">홈</a>
                <a class="nav-link active" href="<c:url value='/books/list' />">도서 관리</a>
                <a class="nav-link" href="#">고객 관리</a>
                <a class="nav-link" href="#">주문 관리</a>
            </div>
        </div>
    </nav>

    <!-- 메인 컨텐츠 -->
    <div class="container main-content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <h2 class="mb-0">
                                <i class="fas fa-book"></i> 도서 목록
                            </h2>
                            <div>
                                <button class="btn btn-success me-2" onclick="alert('도서 추가 기능은 추후 구현됩니다.')">
                                    <i class="fas fa-plus"></i> 새 도서 추가
                                </button>
                                <button class="btn btn-secondary" onclick="location.reload()">
                                    <i class="fas fa-sync-alt"></i> 새로고침
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty books}">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th width="10%" class="text-center">도서ID</th>
                                                <th width="40%">도서명</th>
                                                <th width="25%">출판사</th>
                                                <th width="15%" class="text-end">가격</th>
                                                <th width="10%" class="text-center">관리</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${books}" var="book">
                                                <tr>
                                                    <td class="text-center">
                                                        <span class="badge bg-primary">${book.bookId}</span>
                                                    </td>
                                                    <td>
                                                        <strong>${book.bookName}</strong>
                                                    </td>
                                                    <td>
                                                        <span class="text-muted">${book.publisher}</span>
                                                    </td>
                                                    <td class="text-end">
                                                        <span class="price">
                                                            <fmt:formatNumber value="${book.price}" pattern="#,###"/>원
                                                        </span>
                                                    </td>
                                                    <td class="text-center">
                                                        <div class="btn-group btn-group-sm">
                                                            <button type="button" class="btn btn-outline-info" title="상세보기">
                                                                <i class="fas fa-eye"></i>
                                                            </button>
                                                            <button type="button" class="btn btn-outline-warning" title="수정">
                                                                <i class="fas fa-edit"></i>
                                                            </button>
                                                            <button type="button" class="btn btn-outline-danger" title="삭제"
                                                                    onclick="confirmDelete('${book.bookName}')">
                                                                <i class="fas fa-trash"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                
                                <!-- 하단 정보 -->
                                <div class="d-flex justify-content-between align-items-center mt-3">
                                    <div class="text-muted">
                                        총 <strong class="text-primary">${totalBooks}</strong>권의 도서가 등록되어 있습니다.
                                    </div>
                                    <nav>
                                        <ul class="pagination pagination-sm mb-0">
                                            <li class="page-item disabled">
                                                <span class="page-link">이전</span>
                                            </li>
                                            <li class="page-item active">
                                                <span class="page-link">1</span>
                                            </li>
                                            <li class="page-item disabled">
                                                <span class="page-link">다음</span>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="text-center py-5">
                                    <i class="fas fa-book fa-4x text-muted mb-3"></i>
                                    <h4 class="text-muted">등록된 도서가 없습니다</h4>
                                    <p class="text-muted">새로운 도서를 추가해주세요.</p>
                                    <button class="btn btn-primary" onclick="alert('도서 추가 기능은 추후 구현됩니다.')">
                                        <i class="fas fa-plus"></i> 첫 번째 도서 추가하기
                                    </button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmDelete(bookName) {
            if (confirm('정말로 "' + bookName + '" 도서를 삭제하시겠습니까?')) {
                alert('삭제 기능은 추후 구현됩니다.');
            }
        }
    </script>
</body>
</html>

<!-- ========================= 3. 에러 페이지 (error.jsp) ========================= -->
<!-- 파일위치: src/main/webapp/WEB-INF/views/error.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류 - 서점 관리 시스템</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card border-danger">
                    <div class="card-header bg-danger text-white">
                        <h4 class="mb-0"><i class="fas fa-exclamation-triangle"></i> 오류 발생</h4>
                    </div>
                    <div class="card-body text-center">
                        <i class="fas fa-bug fa-4x text-danger mb-3"></i>
                        <h5>시스템 오류가 발생했습니다</h5>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger mt-3">
                                ${errorMessage}
                            </div>
                        </c:if>
                        <div class="mt-4">
                            <a href="<c:url value='/' />" class="btn btn-primary me-2">
                                <i class="fas fa-home"></i> 홈으로 돌아가기
                            </a>
                            <button onclick="history.back()" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i> 이전 페이지
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
```
