import { ReactComponent as ArrowIcon } from 'assets/image/arrow.svg';
import ProductPrice from 'components/ProductPrice';

import './styles.css';

const ProductDetails = () => {
  return (
    <div className="product-details-container">
      <div className="base-card product-details-card">
        <div className="goback-container">
          <ArrowIcon />
          <h2>Voltar</h2>
        </div>
        <div className="row">
          <div className="col-xl-6">
            <div className="img-container">
                <img src="https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg" alt="Nome product" />
            </div>
            <div className="name-price-container">
                <h1>Nome do produto</h1>
                <ProductPrice price={2345.67} />
            </div>
          </div>
          <div className="col-xl-6">
            <div className="description-container">
                <h2>Descrição produto</h2>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Reprehenderit voluptatibus, ratione iste cumque fugit quas accusantium culpa quae, sapiente deserunt minima, adipisci dignissimos voluptas eaque. Suscipit, necessitatibus iste! Cum, aliquam?</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;