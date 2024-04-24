import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tour-extra.reducer';

export const TourExtraDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tourExtraEntity = useAppSelector(state => state.tourExtra.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tourExtraDetailsHeading">
          <Translate contentKey="xploraAdminApp.tourExtra.detail.title">TourExtra</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.tourExtra.code">Code</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.tourExtra.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="xploraAdminApp.tourExtra.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.icon}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="xploraAdminApp.tourExtra.price">Price</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.price}</dd>
          <dt>
            <span id="offer">
              <Translate contentKey="xploraAdminApp.tourExtra.offer">Offer</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.offer}</dd>
          <dt>
            <span id="shopProductId">
              <Translate contentKey="xploraAdminApp.tourExtra.shopProductId">Shop Product Id</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.shopProductId}</dd>
          <dt>
            <span id="shopUrl">
              <Translate contentKey="xploraAdminApp.tourExtra.shopUrl">Shop Url</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.shopUrl}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.tourExtra.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {tourExtraEntity.createdDate ? (
              <TextFormat value={tourExtraEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="defaultImage">
              <Translate contentKey="xploraAdminApp.tourExtra.defaultImage">Default Image</Translate>
            </span>
          </dt>
          <dd>{tourExtraEntity.defaultImage}</dd>
          <dt>
            <span id="defaultImageData">
              <Translate contentKey="xploraAdminApp.tourExtra.defaultImageData">Default Image Data</Translate>
            </span>
          </dt>
          <dd>
            {tourExtraEntity.defaultImageData ? (
              <div>
                {tourExtraEntity.defaultImageDataContentType ? (
                  <a onClick={openFile(tourExtraEntity.defaultImageDataContentType, tourExtraEntity.defaultImageData)}>
                    <img
                      src={`data:${tourExtraEntity.defaultImageDataContentType};base64,${tourExtraEntity.defaultImageData}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {tourExtraEntity.defaultImageDataContentType}, {byteSize(tourExtraEntity.defaultImageData)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourExtra.createdBy">Created By</Translate>
          </dt>
          <dd>{tourExtraEntity.createdBy ? tourExtraEntity.createdBy.login : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourExtra.tags">Tags</Translate>
          </dt>
          <dd>
            {tourExtraEntity.tags
              ? tourExtraEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourExtraEntity.tags && i === tourExtraEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourExtra.category">Category</Translate>
          </dt>
          <dd>
            {tourExtraEntity.categories
              ? tourExtraEntity.categories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourExtraEntity.categories && i === tourExtraEntity.categories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tourExtra.tours">Tours</Translate>
          </dt>
          <dd>
            {tourExtraEntity.tours
              ? tourExtraEntity.tours.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {tourExtraEntity.tours && i === tourExtraEntity.tours.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tour-extra" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tour-extra/${tourExtraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TourExtraDetail;
