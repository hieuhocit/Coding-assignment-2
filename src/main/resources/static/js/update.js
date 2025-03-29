const modal = document.getElementById("myModal");

const editAuthorBtns = [...document.getElementsByClassName("edit-author")];
const editBookBtns = [...document.getElementsByClassName("edit-book")];
const editReviewBtns = [...document.getElementsByClassName("edit-review")];

const closeModalSpan = document.querySelector(".modal-close");
const modalTitle = document.querySelector(".modal-title");
const modalHtml = document.querySelector(".modal-html");

closeModalSpan.onclick = function() {
  modal.style.display = "none";
  modalHtml.innerHTML = "";
};

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        modalHtml.innerHTML = "";
    }
};

editAuthorBtns.forEach((btn) => {
  btn.onclick = async function() {
    modal.style.display = "flex";
    const type = btn.getAttribute("data-type");
    const saveChangesBtn = document.getElementById("modal-save-btn");

    if(type === 'author') {
      const authorId = btn.getAttribute("data-id");
      const authorName = btn.getAttribute("data-name");

      modalTitle.textContent = "Edit Author";

      const authorNameElement = document.createElement("input");
      authorNameElement.setAttribute("type", "text");
      authorNameElement.setAttribute("placeholder", "Author Name");
      authorNameElement.setAttribute("value", authorName);
      authorNameElement.setAttribute("required", "required");

      const authorLabelElement = document.createElement("label");
      authorLabelElement.setAttribute("for", "author-name");
      authorLabelElement.textContent = "Name";

      const errorMessageElement = document.createElement("div");
      errorMessageElement.textContent = "* Tên không được để trống.";
      errorMessageElement.style.color = "red";
      errorMessageElement.style.display = "none";
      errorMessageElement.style.fontSize = "14px";
      errorMessageElement.style.marginTop = "-20px";
      errorMessageElement.style.marginBottom = "12px";
      
      modalHtml.appendChild(authorLabelElement);
      modalHtml.appendChild(authorNameElement);
      modalHtml.appendChild(errorMessageElement);

      saveChangesBtn.onclick = async function() {
        const authorNameInput = authorNameElement.value;

        if(!authorNameInput || authorNameInput.trim() === "") {
          errorMessageElement.style.display = "block";
          return;
        }

        const base_url = window.location.origin;
        const url = `${base_url}/${authorId}`;

        try {
          const response = await fetch(url, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify({ name: authorNameInput })
          });

          errorMessageElement.style.display = "none";
          modalHtml.innerHTML = "";
          modal.style.display = "none";

          window.location.reload();
        } catch (error) {
          alert("Đã xảy ra lỗi khi cập nhật tác giả.");
        }
       
      };
    }
  };
});

editBookBtns.forEach((btn) => {
  btn.onclick = async function() {
    modal.style.display = "flex";
    const type = btn.getAttribute("data-type");
    const saveChangesBtn = document.getElementById("modal-save-btn");

    if(type === 'book') {
      const bookId = btn.getAttribute("data-id");
      const title = btn.getAttribute("data-title");
      const authorId = btn.getAttribute("data-authorId");

      modalTitle.textContent = "Edit Book";
      
      const bookTitleElement = document.createElement("input");
      bookTitleElement.setAttribute("type", "text");
      bookTitleElement.setAttribute("placeholder", "Book Title");
      bookTitleElement.setAttribute("value", title);
      bookTitleElement.setAttribute("required", "required");

      const bookLabelElement = document.createElement("label");
      bookLabelElement.textContent = "Title";

      const errorTitleMessageElement = document.createElement("div");
      errorTitleMessageElement.textContent = "* Tiêu đề không được để trống.";
      errorTitleMessageElement.style.color = "red";
      errorTitleMessageElement.style.display = "none";
      errorTitleMessageElement.style.fontSize = "14px";
      errorTitleMessageElement.style.marginTop = "-20px";
      errorTitleMessageElement.style.marginBottom = "12px";

      const errorAuthorMessageElement = document.createElement("div");
      errorAuthorMessageElement.textContent = "* Vui lòng chọn tác giả.";
      errorAuthorMessageElement.style.color = "red";
      errorAuthorMessageElement.style.display = "none";
      errorAuthorMessageElement.style.fontSize = "14px";
      errorAuthorMessageElement.style.marginTop = "-20px";
      errorAuthorMessageElement.style.marginBottom = "12px";


      const authorLabelElement = document.createElement("label");
      authorLabelElement.textContent = "Author";

      const authorSelectionElement = document.createElement("select");
      authorSelectionElement.setAttribute("name", "author");

      const authors = await (await fetch("/api/authors")).json();

      authors.forEach((author) => {
        const option = document.createElement("option");
        option.value = author.id;
        option.textContent = author.name;
        if(author.id === +authorId) {
          option.selected = true;
        }
        authorSelectionElement.appendChild(option);
      })

      
      modalHtml.appendChild(bookLabelElement);
      modalHtml.appendChild(bookTitleElement);
      modalHtml.appendChild(errorTitleMessageElement);

      modalHtml.appendChild(authorLabelElement);
      modalHtml.appendChild(authorSelectionElement);
      modalHtml.appendChild(errorAuthorMessageElement);

      saveChangesBtn.onclick = async function() {
        const bookTitleInput = bookTitleElement.value;
        const authorIdInput = authorSelectionElement.value;

        let isValid = true;

        if(!bookTitleInput || bookTitleInput.trim() === "") {
          errorTitleMessageElement.style.display = "block";
          isValid = false;
        }

        if(!authorIdInput || authorIdInput.trim() === "") {
          errorAuthorMessageElement.style.display = "block";
          isValid = false;
        }

        if(!isValid) {
          return;
        }
        

        try {
          const response = await fetch(`/books/${bookId}`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify({ title:bookTitleInput, authorId:authorIdInput })
          });

          errorTitleMessageElement.style.display = "none";
          errorAuthorMessageElement.style.display = "none";
          modalHtml.innerHTML = "";
          modal.style.display = "none";

          window.location.reload();
        } catch (error) {
          alert("Đã xảy ra lỗi khi cập nhật sách.");
        }
       
      };
    }
  };
});

editReviewBtns.forEach((btn) => {
  btn.onclick = async function() {
    modal.style.display = "flex";
    const type = btn.getAttribute("data-type");
    const saveChangesBtn = document.getElementById("modal-save-btn");

    if(type === 'review') {
      const reviewId = btn.getAttribute("data-id");
      const content = btn.getAttribute("data-content");
      const bookId = btn.getAttribute("data-bookId");

      modalTitle.textContent = "Edit Review";
      
      const reviewElement = document.createElement("textarea");
      reviewElement.setAttribute("placeholder", "Review Content");
      reviewElement.setAttribute("required", "required");
      reviewElement.style.height = "120px";
      reviewElement.value = content;

      const reviewLabelElement = document.createElement("label");
      reviewLabelElement.textContent = "Review";

      const errorReviewMessageElement = document.createElement("div");
      errorReviewMessageElement.textContent = "* Nội dung đánh giá không được để trống.";
      errorReviewMessageElement.style.color = "red";
      errorReviewMessageElement.style.display = "none";
      errorReviewMessageElement.style.fontSize = "14px";
      errorReviewMessageElement.style.marginTop = "-20px";
      errorReviewMessageElement.style.marginBottom = "12px";

      const errorBookMessageElement = document.createElement("div");
      errorBookMessageElement.textContent = "* Vui lòng chọn sách.";
      errorBookMessageElement.style.color = "red";
      errorBookMessageElement.style.display = "none";
      errorBookMessageElement.style.fontSize = "14px";
      errorBookMessageElement.style.marginTop = "-20px";
      errorBookMessageElement.style.marginBottom = "12px";


      const bookLabelElement = document.createElement("label");
      bookLabelElement.textContent = "Book";

      const bookSelectionElement = document.createElement("select");
      bookSelectionElement.setAttribute("name", "book");
      bookSelectionElement.disabled = true;
      bookSelectionElement.style.cursor = "not-allowed";

      const books = await (await fetch("/api/books")).json();

      books.forEach((book) => {
        const option = document.createElement("option");
        option.value = book.id;
        option.textContent = book.title;
        if(book.id === +bookId) {
          option.selected = true;
        }
        bookSelectionElement.appendChild(option);
      })

      modalHtml.appendChild(bookLabelElement);
      modalHtml.appendChild(bookSelectionElement);
      modalHtml.appendChild(errorBookMessageElement);
      
      modalHtml.appendChild(reviewLabelElement);
      modalHtml.appendChild(reviewElement);
      modalHtml.appendChild(errorReviewMessageElement);


      saveChangesBtn.onclick = async function() {
        const contentInput = reviewElement.value;
        const bookIdInput = bookSelectionElement.value;

        let isValid = true;

        if(!contentInput || contentInput.trim() === "") {
          errorReviewMessageElement.style.display = "block";
          isValid = false;
        }

        if(!bookIdInput || bookIdInput.trim() === "") {
          errorBookMessageElement.style.display = "block";
          isValid = false;
        }

        if(!isValid) {
          return;
        }
        

        try {
          const response = await fetch(`/reviews/${reviewId}`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify({ content:contentInput, bookId:bookIdInput })
          });

          errorReviewMessageElement.style.display = "none";
          errorBookMessageElement.style.display = "none";
          modalHtml.innerHTML = "";
          modal.style.display = "none";

          window.location.reload();
        } catch (error) {
          alert("Đã xảy ra lỗi khi cập nhật đánh giá.");
        }
       
      };
    }
  };
});

