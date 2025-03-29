const formAuthor = document.querySelector("#create-author-form");
const formBook = document.querySelector("#create-book-form");
const formReview = document.querySelector("#create-review-form");


const createAuthor = async e => {
    e.preventDefault();

    const formData = new FormData(formAuthor);
    const name = formData.get("name");

    const errorElement = document.querySelector("#error-message");
    if(!name || name.trim() === "") {
       errorElement.textContent = "* Tên tác giả không được để trống";
       return;
    }else {
       errorElement.textContent = "";
    }

    try {
        const response = await fetch("/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({name})
        });

        if (response.ok) {
            window.location.reload();
        } else {
            alert("Lỗi khi tạo tác giả");
        }
    } catch (error) {
        alert("Lỗi khi tạo tác giả");
    }
}

const createBook = async e => {
    e.preventDefault();

    const formData = new FormData(formBook);
    const title = formData.get("title");
    const authorId = formData.get("author");

    const errorTitleElement = document.querySelector("#error-title-message");
    const errorAuthorElement = document.querySelector("#error-author-message");

    let isValid = true;

    if(!title || title.trim() === "") {
       errorTitleElement.textContent = "* Tên sách không được để trống";
       isValid = false;
    }

    if(!authorId || authorId.trim() === "") {
       errorAuthorElement.textContent = "* Tác giả không được để trống";
       isValid = false;
    }

    if(!isValid) {
       return;
    }
    

    try {
        const response = await fetch("/books", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({title, authorId})
        });

        if (response.ok) {
            window.location.reload();
        } else {
            alert("Lỗi khi tạo sách");
        }
    } catch (error) {
        alert("Lỗi khi tạo sách");
    }
}

const createReview = async e => {
    e.preventDefault();

    const formData = new FormData(formReview);
    const review = formData.get("review");
    const bookId = formData.get("book");

    const errorBookMessage = document.querySelector("#error-book-message");
    const errorReviewMessage = document.querySelector("#error-review-message");

    let isValid = true;

    if(!bookId || bookId.trim() === "") {
       errorBookMessage.textContent = "* Vui lòng chọn sách.";
       isValid = false;
    }

    if(!review || review.trim() === "") {
       errorReviewMessage.textContent = "* Nội dung đánh giá không được để trống.";
       isValid = false;
    }

    if(!isValid) {
       return;
    }
    

    try {
        const response = await fetch("/reviews", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({content: review, bookId:bookId})
        });

        if (response.ok) {
            window.location.reload();
        } else {
            alert("Lỗi khi tạo đánh giá");
        }
    } catch (error) {
        alert("Lỗi khi tạo đánh giá");
    }
}

formAuthor?.addEventListener("submit", createAuthor);
formBook?.addEventListener("submit", createBook);
formReview?.addEventListener("submit", createReview);
