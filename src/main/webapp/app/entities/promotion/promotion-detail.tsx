import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './promotion.reducer';

export const PromotionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const promotionEntity = useAppSelector(state => state.promotion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="promotionDetailsHeading">
          <Translate contentKey="xploraAdminApp.promotion.detail.title">Promotion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{promotionEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.promotion.code">Code</Translate>
            </span>
          </dt>
          <dd>{promotionEntity.code}</dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="xploraAdminApp.promotion.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{promotionEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="discount">
              <Translate contentKey="xploraAdminApp.promotion.discount">Discount</Translate>
            </span>
          </dt>
          <dd>{promotionEntity.discount}</dd>
          <dt>
            <span id="fromDate">
              <Translate contentKey="xploraAdminApp.promotion.fromDate">From Date</Translate>
            </span>
          </dt>
          <dd>
            {promotionEntity.fromDate ? <TextFormat value={promotionEntity.fromDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="toDate">
              <Translate contentKey="xploraAdminApp.promotion.toDate">To Date</Translate>
            </span>
          </dt>
          <dd>
            {promotionEntity.toDate ? <TextFormat value={promotionEntity.toDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.promotion.tour">Tour</Translate>
          </dt>
          <dd>
            {promotionEntity.tours
              ? promotionEntity.tours.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.code}</a>
                    {promotionEntity.tours && i === promotionEntity.tours.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/promotion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/promotion/${promotionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PromotionDetail;
