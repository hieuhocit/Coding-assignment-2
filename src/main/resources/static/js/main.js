document.addEventListener("DOMContentLoaded", () => {
  const subMenuItems = [...document.querySelectorAll(".submenu-item")];
  const menuTitleItems = [...document.querySelectorAll(".menu-title")];
  
  const routingElement = document.querySelector("#routing");
  
  const ENDPOINTS = {
    '/': 'Authors > List',
    '/create': 'Authors > Create',
    '/books': 'Books > List',
    '/books/create': 'Books > Create',
    '/reviews': 'Reviews > List',
    '/reviews/create': 'Reviews > Create',
  }

  const path = window.location.pathname;
  routingElement.textContent = ENDPOINTS[path];
  
  menuTitleItems.forEach(item => {
    item.addEventListener("click", (e) => {
      const element = e.currentTarget;
      const siblingElement = element.nextElementSibling;
      const arrowIconElement = element.querySelector(".arrow");
  
      const isOpen = siblingElement.classList.contains("open");
      if(isOpen) {
        siblingElement.classList.remove("open");
        siblingElement.classList.add("close");
        arrowIconElement.classList.add("close");
        arrowIconElement.classList.remove("open");
      }else {
        siblingElement.classList.remove("close");
        siblingElement.classList.add("open");
        arrowIconElement.classList.add("open");
        arrowIconElement.classList.remove("close");
      }
    });


  });
  
  subMenuItems.forEach(item => {
    item.addEventListener("click", (e) => {
      const endpoint = e.target.dataset.endpoint;
      routingElement.textContent = ENDPOINTS[endpoint];
      window.location.href = endpoint;
    })
  })
});
