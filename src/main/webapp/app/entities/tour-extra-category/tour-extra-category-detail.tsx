import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tour-extra-category.reducer';

export const TourExtraCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tourExtraCategoryEntity = useAppSelector(state => state.tourExtraCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tourExtraCategoryDetailsHeading">
          <Translate contentKey="xploraAdminApp.tourExtraCategory.detail.title">TourExtraCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tourExtraCategoryEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.code">Code</Translate>
            </span>
          </dt>
          <dd>{tourExtraCategoryEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{tourExtraCategoryEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{tourExtraCategoryEntity.icon}</dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{tourExtraCategoryEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {tourExtraCategoryEntity.defaultImageData ? (
              <div>
                {tourExtraCategoryEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(tourExtraCategoryEntity.defaultImageDataContentType, tourExtraCategoryEntity.defaultImageData)}>
                    <img
                      src={`data:${tourExtraCategoryEntity.defaultImageDataContentType};base64,${tourExtraCategoryEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {tourExtraCategoryEntity.defaultImageDataContentType}, {byteSize(tourExtraCategoryEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="shopCategoryId">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.shopCategoryId">Shop Category Id</Translate>
            </span>
          </dt>
          <dd>{tourExtraCategoryEntity.shopCategoryId}</dd>
          <dt>
            <span id="shopUrl">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.shopUrl">Shop Url</Translate>
            </span>
          </dt>
          <dd>{tourExtraCategoryEntity.shopUrl}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {tourExtraCategoryEntity.createdDate ? (
              <TextFormat value={tourExtraCategoryEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourExtraCategory.createdBy">Created By</Translate>
          </dt>
          <dd>{tourExtraCategoryEntity.createdBy ? tourExtraCategoryEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourExtraCategory.extra">Extra</Translate>
          </dt>
          <dd>
            {tourExtraCategoryEntity.extras
              ? tourExtraCategoryEntity.extras.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourExtraCategoryEntity.extras && i === tourExtraCategoryEntity.extras.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tour-extra-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tour-extra-category/${tourExtraCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TourExtraCategoryDetail;
