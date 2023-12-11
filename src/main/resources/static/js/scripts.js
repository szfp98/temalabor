
        function getCartId(userId) {
            return fetch('/api/cart?userId=' + userId)
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to retrieve cart');
                    }
                })
                .then(data => {
                    return data.length > 0 ? data[0].id : null;
                });
        }
    
        function addToCart(button) {
            let productId = button.getAttribute('data-product-id');
            let userId = 1;  // Replace this with logic to retrieve the current user's ID
            let quantity = 1;
    
            getCartId(userId).then(cartId => {
                if (cartId) {
                    fetch('/api/cart', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: 'id=' + 1 + '&pId=' + productId + '&qty=' + quantity + '&userId=' + userId
                    })
                    .then(response => {
                        if (response.ok) {
                            alert("Product added to cart!");
                        } else {
                            alert("Failed to add product to cart.");
                        }
                    })
                    .catch(error => console.error('Error:', error));
                } else {
                    alert("No cart found for user.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Failed to retrieve cart information.");
            });
        }

        function deleteProduct(button) {
            let productId = button.getAttribute('data-ci-id');
            let userId = 1; 
    
            getCartId(userId).then(cartId => {
                if (cartId) {
                    fetch('/api/cart', {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: 'cId=' + 1 + '&cartItemId=' + productId
                    })
                    .then(response => {
                        if (response.ok) {
                            alert("Product deleted from cart!");
                            window.location.reload();
                        } else {
                            alert("Failed to delete product from cart.");
                        }
                    })
                    .catch(error => console.error('Error:', error));
                } else {
                    alert("No cart found for user.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Failed to retrieve cart information.");
            });
        }
    